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

import org.katkov.lj.xmlrpc.SecurityType;

import java.util.Map;

public class PostEventArgument extends BaseArgument {

    /**
     * @param event The event/log text the user is submitting. Carriage returns are okay (0x0A, 0x0A0D, or 0x0D0A), although 0x0D are removed internally to make everything into Unix-style line-endings (just \ns). Posts may also contain HTML, but be aware that the LiveJournal server converts newlines to HTML <BR>s when displaying them, so your client should not try to insert these itself.
     * @@required
     */
    public void setEvent(String event) {
        struct.put("event", event);
    }

    /**
     * @param lineendings
     * @@required Specifies the type of line-endings you're using. Possible values are unix (0x0A (\n)), pc (0x0D0A (\r\n)), or mac ( 0x0D (\r) ). The default is not-Mac. Internally, LiveJournal stores all text as Unix-form atted text, and it does the conversion by removing all \r characters. If you're sending a multi-line event on Mac, you have to be sure and send a lineendings value of mac or you r line endings will be removed. PC and Unix clients can ignore this setting, or you can s end it. It may be used for something more results the future.
     */
    public void setLineendings(String lineendings) {
        struct.put("lineendings", lineendings);
    }

    /**
     * @param subject The subject for this post. Limited to 255 characters. No newlines.
     * @@required
     */
    public void setSubject(String subject) {
        struct.put("subject", subject);
    }

    /**
     * @param security Specifies who can read this post. Valid values are public (default), private and usemask. When value is usemask, viewability is controlled by the allowmask.
     * @@optional
     */
    public void setSecurity(SecurityType security) {
        if (security != null) {
            struct.put("security", security.getCode());
        }
    }

    /**
     * @param allowmask Relevant when security is usemask. A 32-bit unsigned integer representing which of the user's groups of friends are allowed to view this post. Turn bit 0 on to allow any defined friend to read it. Otherwise, turn bit 1-30 on for every friend group that should be allowed to read it. Bit 31 is reserved.
     * @@optional
     */
    public void setAllowmask(Integer allowmask) {
        struct.put("allowmask", allowmask);
    }

    /**
     * Set arbitrary (but restricted) meta-data properties to this log item. See Chapter 12: Journal Item Meta-data for the documentation of the keys and value data types.
     *
     * @param props
     * @@optionlal
     */
    public void setProps(Map props) {
        //TODO: implement this
        throw new RuntimeException("Not supported");
    }

    /**
     * @param usejournal If posting to a shared journal, include this key and the username you wish to post to. By default, you post to the journal of "user" as specified above.
     * @@optional
     */
    public void setUsejournal(String usejournal) {
        struct.put("usejournal", usejournal);
    }


    /**
     * @param year The current 4-digit year (from the user's local timezone).
     * @@required
     */
    public void setYear(int year) {
        struct.put("year", year);
    }

    /**
     * @param mon The current 1- or 2-digit month (from the user's local timezone). 1 is January
     * @@required
     */
    public void setMon(int mon) {
        struct.put("mon", mon);
    }

    /**
     * @param day The current 1- or 2-digit day of the month (from the user's local timezone).
     * @@required
     */
    public void setDay(int day) {
        struct.put("day", day);
    }

    /**
     * @param hour The current 1- or 2-digit hour from 0 to 23 (from the user's local timezone).
     * @@required
     */
    public void setHour(int hour) {
        struct.put("hour", hour);
    }

    /**
     * @param min The current 1- or 2-digit minute (from the user's local timezone).
     * @@required
     */
    public void setMin(int min) {
        struct.put("min", min);
    }

}
