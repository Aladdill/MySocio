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

package org.katkov.lj.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Date;

public abstract class ProcessProfileCommand implements ProcessCommand<UserProfile, String> {
    private Log logger = LogFactory.getLog(ProcessProfileCommand.class);
    protected HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
    
    private Extractor<String> liveJournalProfileLocationExtractor = new LiveJournalProfileLocationExtractor();
    private Extractor<String> userpickExtractor = new LiveJournalProfileUserpickExtractor();
    private Extractor<String> websiteExtractor = new LiveJournalProfileWebsiteExtractor();
    private Extractor<Date> birthdateExtractor = new LiveJournalProfileBirthdateExtractor();
    private Extractor<String> emailExtractor = new LiveJournalProfileEmailExtractor();

    public UserProfile execute(String name, String profileURL, String blogURL, int timeout) throws Exception {
        logger.debug("Entering execute(" + name + ", " + profileURL + ", " + blogURL + ", " + timeout + ")");
        String content = fetchURLContent(profileURL, timeout);
        UserProfile userProfile = new UserProfile();
        userProfile.setName(name);
        userProfile.setLocation(liveJournalProfileLocationExtractor.extract(content));
        userProfile.setWebsite(websiteExtractor.extract(content));
        userProfile.setUserpic(userpickExtractor.extract(content));
        userProfile.setBirthdate(birthdateExtractor.extract(content));
        userProfile.setEmail(emailExtractor.extract(content));
        userProfile.setFriends(getFriends(name, content));
        userProfile.setFriendOfs(getFriendOfs(name, content));
        userProfile.setBlog(blogURL);
        logger.trace("Exiting execute");
        return userProfile;
    }

    protected String fetchURLContent(String uri, int timeout) throws Exception {
        logger.debug("Entering fetchURLContent(" + uri + "," + timeout + ")");
        HttpMethod method = new GetMethod(uri);
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

        client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                throw new IOException("HTTP server returned error code - " + Integer.toString(statusCode));
            }
            // Read the response body.
            String string = method.getResponseBodyAsString();
            logger.trace("Exiting fetchURLContent");
            return string;
        }
        finally {
            method.releaseConnection();
        }

    }

    abstract String[] getFriends(String userName, String content) throws Exception;

    abstract String[] getFriendOfs(String userName, String content) throws Exception;

}
