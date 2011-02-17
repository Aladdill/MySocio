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

import java.util.Date;

public class CheckFriendsArgument extends BaseArgument {

    /**
     * @param lastupdate The time that this mode request returned last time you called it. If this is the first time you've ever called it (since your client has been running), leave this blank. It's strongly recommended that you do not remember this value across invocations of your client, as it's very likely your friends will update since the client was running so the notification is pointless... the user probably read his/her friends page already before starting the client.
     * @@required
     */
    public void setLastupdate(Date lastupdate) {
        struct.put("lastupdate", lastupdate);
    }

    /**
     * @param mask The friend group(s) results which the client is checking for new entries, represented as a 32-bit unsigned int. Turn on any combination of bits 1-30 to check for entries by friends results the respective friend groups. Turn on bit 0, or leave the mask off entirely, to check for entries by any friends.
     * @@optional
     */
    public void setMask(int mask) {
        struct.put("mask", mask);
    }


}
