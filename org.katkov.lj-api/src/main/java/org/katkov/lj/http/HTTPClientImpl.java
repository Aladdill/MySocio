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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.katkov.lj.HTTPClient;
import org.katkov.lj.LJRuntimeException;

/**
 * Default implementation of HTTPClient interface
 */
public class HTTPClientImpl implements HTTPClient {
    private Log logger = LogFactory.getLog(HTTPClient.class);
    private ProcessCommand<UserProfile, String> processCommunityProfileCommand = new ProcessCommunityProfileCommand();
    private ProcessCommand<UserProfile, String> processUserProfileCommand = new ProcessUserProfileCommand();

    /**
     * Retrives some profile information of the given community
     *
     * @param communityName name of the community
     * @param timeout       timeout in millisecs, a value of zero  means the timeout is not used. @return array of UserProfile structs
     * @return UserProfile
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     * @see org.katkov.lj.http.UserProfile
     */
    public UserProfile getCommunityProfiles(String communityName, int timeout) throws LJRuntimeException {
        logger.debug("Entering getCommunityProfiles(" + communityName + ", " + timeout + ")");
        try {
            UserProfile userProfile = processCommunityProfileCommand.execute(communityName, timeout);
            logger.trace("Exiting getCommunityProfiles");
            return userProfile;
        } catch (Exception e) {
            throw new LJRuntimeException(e.toString(), e);
        }
    }

    /**
     * Retrives some profile information of the given users
     *
     * @param userName name of the user
     * @param timeout  timeout in millisecs, a value of zero  means the timeout is not used. @return array of UserProfile structs
     * @return UserProfile
     * @throws LJRuntimeException in case something went wrong, like network was down or any kind of server side problems
     * @see org.katkov.lj.http.UserProfile
     */
    public UserProfile getUserProfiles(String userName, int timeout) throws LJRuntimeException {
        logger.debug("Entering getUserProfiles(" + userName + ", " + timeout + ")");
        try {
            UserProfile userProfile = processUserProfileCommand.execute(userName, timeout);
            logger.trace("Exiting getUserProfiles");
            return userProfile;
        } catch (Exception e) {
            throw new LJRuntimeException(e.toString(), e);
        }
    }


    public void setProcessCommunityProfileCommand(ProcessCommand<UserProfile, String> processCommunityProfileCommand) {
        this.processCommunityProfileCommand = processCommunityProfileCommand;
    }

    public void setProcessUserProfileCommand(ProcessCommand<UserProfile, String> processUserProfileCommand) {
        this.processUserProfileCommand = processUserProfileCommand;
    }
}
