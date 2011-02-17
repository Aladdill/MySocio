package org.katkov.lj;

import org.katkov.lj.comments.Comment;
import org.katkov.lj.comments.CommentsMetaData;

/**
 * LiveJournal provides an interface for exporting comments using an XML format that makes it easy for people to write
 * utilities to use the information. A user is allowed to download comments for any journal they administrate.
 * <br/>
 * In order to use the comment exporter, you will need to have a valid session cookie. This can be obtained with the
 * sessiongenerate call of XML-RPC or by posting login information to the login.bml page.
 * <br/>
 * All methods throw LJException runtime exceptions, which represent XML-RPC errors and server side problems.
 * <br/>
 * Reasoning: Some cases occur where specific types of exceptions might usefully be caught and handled, but
 * only a minority of callers would want to handle the problem themselves.
 * Rather than make the rest of the classes pay for possibility in the form of catching and rethrowing these
 * exceptions, unchecked exceptions are used.
 *
 * @see org.katkov.lj.XMLRPCClient
 * @see org.katkov.lj.HTTPClient
 */
public interface CommentsClient {
    /**
     * It is a lightweight call that returns a small XML file that provides basic information on each comment
     * posted in a journal.
     *
     * @param authCookie valid session cookie This can be obtained with the sessiongenerate call of XML-RPC or by
     *                   posting login information to the login.bml page.
     * @param timeout
     * @return CommentsMetaData
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     * @see org.katkov.lj.comments.CommentsMetaData
     */
    CommentsMetaData getCommentsMetaData(String authCookie, int timeout);

    /**
     * Retrieves comments themselves.
     * Comment body data is to be heavily cached. None of this data can change. Once you have downloaded a comment,
     * you do not need to do so again.
     *
     * @param authCookie valid session cookie This can be obtained with the sessiongenerate call of XML-RPC or by
     *                   posting login information to the login.bml page.
     * @param metaData   CommentsMetaData obtained via getCommentsMetaData() call
     * @param timeout
     * @return array of comments
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     * @see org.katkov.lj.comments.Comment
     */
    Comment[] getComments(String authCookie, CommentsMetaData metaData, int timeout);
}
