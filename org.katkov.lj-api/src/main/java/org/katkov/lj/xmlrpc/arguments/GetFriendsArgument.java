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

package org.katkov.lj.xmlrpc.arguments;

public class GetFriendsArgument extends BaseArgument {


    /**
     * @param isInclude If set to true, you will also get back the info from the "friendof" mode.
     *                  Some clients show friends and friendof data in separate tabs/panes.
     *                  If you're always going to load both, then use this flag (as opposed to a tabbed dialog approach,
     *                  where the user may not go to the second tab and thus would not need to load the friendof data.)
     *                  friendof request variables can be used.
     * @@optional
     */
    public void setIncludeFriendOf(boolean isInclude) {
        struct.put("includefriendof", isInclude ? 1 : 0);
    }

    /**
     * @param isInclude If set to true, you will also get back the info from the "getfriendgroups" mode.
     *                  See above for the reason why this would be useful.
     * @@optional
     */
    public void setIncludeGroups(boolean isInclude) {
        struct.put("includegroups", isInclude ? 1 : 0);
    }

    /**
     * @param friendlimit If set to a numeric value greater than zero, this mode will only return the number
     *                    of results indicated. Useful only for building pretty lists for display which might have a button to
     *                    view the full list nearby.
     * @@optional
     */
    public void setFriendLimit(int friendlimit) {
        struct.put("friendlimit", friendlimit);
    }


}
