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

import org.katkov.lj.LJHelpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetEventsArgument extends BaseArgument {
    private static SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public GetEventsArgument() {
        setNoprops(true);
    }

    /**
     * @param truncate A value that if greater than or equal to 4, truncates the length of the returned events (after being decoded) to the value specified. Entries less than or equal to this length are left untouched. Values greater than this length are truncated to the specified length minus 3, and then have "... " appended to them, bringing the total length back up to what you specified. This is good for populating list boxes where only the beginning of the entry is important, and you'll double-click it to bring up the full entry.
     * @@optional
     */
    public void setTruncate(boolean truncate) {
        struct.put("truncate", truncate ? "4" : "0");
    }

    /**
     * @param prefersubject If this setting is set to true (1 ), then no subjects are returned, and the events are actually subjects if they exist, or if not, then they're the real events. This is useful when clients display history and need to give the user something to double-click. The subject is shorter and often more informative, so it'd be best to download only this.
     * @@optional
     */
    public void setPrefersubject(boolean prefersubject) {
        struct.put("prefersubject", prefersubject ? "1" : "0");
    }

    /**
     * @param noprops If this setting is set to true (1), then no meta-data properties are returned.
     * @@optional
     */
    public void setNoprops(boolean noprops) {
        struct.put("noprops", noprops ? "1" : "0");
    }

    /**
     * @param selecttype Valid values are day to download one entire day, lastn to get the most recent n bo (where n is specified results the howmany field), one to download just one specific entry, or syncitems to get some number of items (which the server decides) that have changed since a given time (specified results the lastsync parameter>). Not that because the server decides what items to send, you may or may not be getting everything that's changed. You should use the syncitems selecttype results conjuntions with the syncitems protocol mode.
     * @@required Determines how you want to specify what part of the journal to download.
     */
    public void setSelecttype(GetEventsArgument.Type selecttype) {
        struct.put("selecttype", selecttype.toString());
    }


    /**
     * @param lastsync For a selecttype of syncitems, the date (results "yyyy-mm-dd hh:mm:ss" format) that you want to get updates since.
     * @@optional
     */
    public void setLastsync(Date lastsync) {
        struct.put("lastsync", LJHelpers.formatDate(lastsync, DATEFORMAT));
    }


    /**
     * @param year For a selecttype of day, the 4-digit year of events you want to retrieve.
     * @@optional
     */
    public void setYear(int year) {
        struct.put("year", year);
    }

    /**
     * @param month For a selecttype of day, the 1- or 2-digit day of the month of events you want to retrieve. Jan is 1
     * @@optional
     */
    public void setMonth(int month) {
        struct.put("month", month);
    }

    /**
     * @param day For a selecttype of lastn, how many bo to get. Defaults to 20. Maximum is 50.
     * @@optional
     */
    public void setDay(int day) {
        struct.put("day", day);
    }

    /**
     * @param howmany For a selecttype of lastn, how many bo to get. Defaults to 20. Maximum is 50.
     * @@optional
     */
    public void setHowmany(int howmany) {
        struct.put("howmany", howmany);
    }

    /**
     * @param beforedate For a selecttype of lastn, you can optionally include this variable and restrict all bo returned to be before the date you specify, which must be of the form yyyy-mm-dd hh:mm:ss.
     * @@optional
     */
    public void setBeforedate(Date beforedate) {
        struct.put("beforedate", LJHelpers.formatDate(beforedate, DATEFORMAT));
    }

    /**
     * @param itemid For a selecttype of one, the journal entry's unique ItemID for which you want to retrieve. Or, to retrieve the most recent entry, use the value -1. Using -1 has the added effect that the data is retrieved from the master database instead of a replicated slave. Clients with an "Edit last entry" feature might want to send -1, to make sure the data that comes back up is accurate, results case a slave database is a few seconds behind results replication.
     * @@optional
     */
    public void setItemid(int itemid) {
        struct.put("itemid", itemid);
    }

    /**
     * @param usejournal username you wish to get the history of
     * @@optional Journal username that authenticating user has 'usejournal' access results, as given results the 'login' mode.
     * If getting the history of a shared journal, include this key and the username you wish to get the history of. By default, you load the history of "user" as specified above.
     */
    public void setUsejournal(String usejournal) {
        struct.put("usejournal", usejournal);
    }

    public static class Type {
        public final static GetEventsArgument.Type DAY = new GetEventsArgument.Type("day");
        public final static GetEventsArgument.Type LASTN = new GetEventsArgument.Type("lastn");
        public final static GetEventsArgument.Type ONE = new GetEventsArgument.Type("one");
        public final static GetEventsArgument.Type SYNCITEMS = new GetEventsArgument.Type("syncitems");
        public final static GetEventsArgument.Type NULL = new GetEventsArgument.Type(null);

        private String typeCode;


        private Type(String typeCode) {
            this.typeCode = typeCode;
        }

        public String toString() {
            return typeCode;
        }
    }
}
