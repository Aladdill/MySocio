/*
 * Copyright (c) 2006, Igor Katkov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided 
 * that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions 
 *       and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *       and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * The name of the author may not be used may not be used to endorse or 
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT 
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.katkov.lj.comments;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.katkov.lj.CommentsClient;
import org.katkov.lj.LJHelpers;
import org.katkov.lj.LJRuntimeException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Default implementation of CommentsClient interface
 *
 * @treadsafe by delegation to apache MultiThreadedHttpConnectionManager
 * Several thread might share one instance of CommentsClientImpl
 * @see org.apache.commons.httpclient.MultiThreadedHttpConnectionManager
 */

public class CommentsClientImpl implements CommentsClient {
    private Log logger = LogFactory.getLog(CommentsClient.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH:mm:ss'Z'");

    private DocumentBuilder documentBuilder;
    private String maxComments;
    private String allComments;
    private String id;
    private String posterId;
    private String user;
    private String userMap;
    private String jitemid;
    private String parentid;
    private String state;
    private String date;
    private String body;
    private String subject;
    private final XPath xPath;
    private static final String META_URI = "http://www.livejournal.com/export_comments.bml?get=comment_meta&startid=0";
    private static final String BODY_URI = "http://www.livejournal.com/export_comments.bml?get=comment_body&startid=0";

    private HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());

    public CommentsClientImpl() {
        maxComments = "/livejournal/maxid/text()";
        allComments = "/livejournal/comments/comment";
        id = "@id";
        posterId = "@posterid";
        user = "@user";
        userMap = "/livejournal/usermaps/usermap";
        jitemid = "@jitemid";
        parentid = "@parentid";
        state = "@state";
        date = "date/text()";
        body = "body/text()";
        subject = "subject/text()";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            documentBuilder = factory.newDocumentBuilder();
            xPath = XPathFactory.newInstance().newXPath();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It is a lightweight call that returns a small XML file that provides basic information on each comment
     * posted in a journal.
     *
     * @param authCookie valid session cookie This can be obtained with the sessiongenerate call of XML-RPC or by
     *                   posting login information to the login.bml page.
     * @param timeout    timeout in millisecs, a value of zero  means the timeout is not used.
     * @return CommentsMetaData
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     * @see org.katkov.lj.comments.CommentsMetaData
     */
    public CommentsMetaData getCommentsMetaData(String authCookie, int timeout) throws LJRuntimeException {
        logger.debug("Entering getCommentsMetaData(" + authCookie + ", " + timeout + ")");
        try {
            Document doc = getXMLResponse(META_URI, authCookie, timeout);
            int max = ((Double) xPath.evaluate(maxComments, doc, XPathConstants.NUMBER)).intValue();
            NodeList commentNodes = (NodeList) xPath.evaluate(allComments, doc, XPathConstants.NODESET);
            List<Comment> comments = getAllComments(commentNodes, false);
            Collections.sort(comments);
            Comment lastComment;
            if (comments.size() != 0) {
                lastComment = comments.get(comments.size() - 1);
                if (lastComment.getId() < max) {
                    throw new LJRuntimeException("Error reading comments metadata, try again later");
                }
            }
            NodeList userNodes = (NodeList) xPath.evaluate(userMap, doc, XPathConstants.NODESET);
            CommentsMetaData metaData = new CommentsMetaData(comments, getCommentsUserMap(userNodes), max);
            logger.debug("Exiting getCommentsMetaData");
            return metaData;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }


    /**
     * Retrieves comments themselves.
     * Comment body data is to be heavily cached. None of this data can change. Once you have downloaded a comment,
     * you do not need to do so again.
     *
     * @param authCookie valid session cookie This can be obtained with the sessiongenerate call of XML-RPC or by
     *                   posting login information to the login.bml page.
     * @param metaData   CommentsMetaData obtained via getCommentsMetaData() call
     * @param timeout    timeout in millisecs, a value of zero  means the timeout is not used.
     * @return array of comments
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     * @see org.katkov.lj.comments.Comment
     */
    public Comment[] getComments(String authCookie, CommentsMetaData metaData, int timeout) throws LJRuntimeException {
        logger.debug("Entering getComments(" + authCookie + ", " + timeout + ")");
        try {
            Document doc = getXMLResponse(BODY_URI, authCookie, timeout);
            List<Comment> comments = getAllComments((NodeList) xPath.evaluate(allComments, doc, XPathConstants.NODESET), true);
            Collections.sort(comments);
            Comment[] result;
            if (comments.size() != 0) {
                Comment lastComment = comments.get(comments.size() - 1);
                if (lastComment.getId() < metaData.getMaxId()) {
                    throw new LJRuntimeException("Error reading comments metadata, try again later");
                }
                resolvePosterNames(comments, metaData);
                result = comments.toArray(new Comment[comments.size()]);
            } else {
                result = new Comment[]{};
            }
            logger.debug("Exiting getComments");
            return result;
        } catch (Throwable e) {
            logger.error(e.toString());
            throw new LJRuntimeException(e.getMessage() == null || e.getMessage().length() == 0 ? e.toString() : e.getMessage(), e);
        }
    }

    private Document getXMLResponse(String uri, String authCookie, int timeout) throws IOException, LJRuntimeException, SAXException, XPathExpressionException {
        logger.debug("Entering getXMLResponse(" + uri + "," + authCookie + ", " + timeout + ")");
        HttpMethod method = new GetMethod(uri);
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
        method.setRequestHeader("Cookie", "ljsession=" + authCookie);
        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                throw new IOException("HTTP server returned error code - " + Integer.toString(statusCode));
            }
            // Read the response body.
            logger.debug("Parsing response and creating DOM model");
            Document document = documentBuilder.parse(method.getResponseBodyAsStream());
            logger.debug("Exiting getXMLResponse");
            return document;
        }
        finally {
            method.releaseConnection();
        }
    }

    private List<Comment> getAllComments(NodeList nodes, boolean extended) throws XPathExpressionException, ParseException {
        List<Comment> list = new ArrayList<Comment>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Comment comment;
            int idValue = ((Double) xPath.evaluate(id, nodes.item(i), XPathConstants.NUMBER)).intValue();
            int posterIdValue = ((Double) xPath.evaluate(posterId, nodes.item(i), XPathConstants.NUMBER)).intValue();
            if (extended) {
                int jitemidValue = ((Double) xPath.evaluate(jitemid, nodes.item(i), XPathConstants.NUMBER)).intValue();
                int parentidValue = ((Double) xPath.evaluate(parentid, nodes.item(i), XPathConstants.NUMBER)).intValue();
                CommentState stateValue = CommentState.getInstance((String) xPath.evaluate(state, nodes.item(i), XPathConstants.STRING));
                Date dateValue = LJHelpers.parseDate((String) xPath.evaluate(date, nodes.item(i), XPathConstants.STRING), DATE_FORMAT);
                String subjectValue = (String) xPath.evaluate(subject, nodes.item(i), XPathConstants.STRING);
                String bodyValue = (String) xPath.evaluate(body, nodes.item(i), XPathConstants.STRING);
                comment = new Comment(idValue, posterIdValue, stateValue, jitemidValue, parentidValue, bodyValue, subjectValue, dateValue);
            } else {
                comment = new Comment(idValue, posterIdValue);
            }
            list.add(comment);
        }
        return list;
    }

    private Map<Integer, String> getCommentsUserMap(NodeList nodes) throws XPathExpressionException {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < nodes.getLength(); i++) {
            int key = ((Double) xPath.evaluate(id, nodes.item(i), XPathConstants.NUMBER)).intValue();
            String user = (String) xPath.evaluate(this.user, nodes.item(i), XPathConstants.STRING);
            map.put(key, user);
        }
        return map;
    }

    private void resolvePosterNames(List<Comment> comments, CommentsMetaData metaData) {
        logger.debug("Resolving poster names");
        for (Iterator<Comment> iterator = comments.iterator(); iterator.hasNext();) {
            Comment ljComment = iterator.next();
            if (metaData.getComments().get(ljComment.getId()).getState().equals(CommentState.DELETED_COMMENT)) {
                iterator.remove();
            }
            ljComment.setPosterName(metaData.getUsermap().get(ljComment.getPosterid()));
        }

    }
}
