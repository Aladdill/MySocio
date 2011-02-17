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

package org.katkov.lj.xmlrpc.results;

import org.katkov.lj.LJHelpers;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class SyncResult {
    private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH:mm:ss");
    private int count;
    private Date time;
    private SyncItem[] syncItems;


    public SyncResult(Map map) throws UnsupportedEncodingException, ParseException {
        count = (Integer) map.get("count");
        time = LJHelpers.parseDate((String) map.get("time"), DATEFORMAT);
        syncItems = createSyncGroups((Object[]) map.get("syncitems"));
    }


    public int getCount() {
        return count;
    }

    public Date getTime() {
        return time;
    }

    public SyncItem[] getSyncItems() {
        return syncItems;
    }


    public String toString() {
        return "SyncResult{" +
                "count=" + count +
                ", time=" + time +
                ", syncItems=" + (syncItems == null ? null : Arrays.asList(syncItems)) +
                '}';
    }

    static class SyncItem {
        private String item;
        private String action;
        private Date time;


        public SyncItem(Map map) throws ParseException {
            item = (String) map.get("item");
            action = (String) map.get("action");
            time = LJHelpers.parseDate((String) map.get("time"), DATEFORMAT);
        }


        public String getItem() {
            return item;
        }

        public String getAction() {
            return action;
        }

        public Date getTime() {
            return time;
        }


        public String toString() {
            return "SyncItem{" +
                    "item='" + item + '\'' +
                    ", action='" + action + '\'' +
                    ", time=" + time +
                    '}';
        }
    }

    public static SyncItem[] createSyncGroups(Object[] objects) throws ParseException {
        if (objects == null) {
            return null;
        }
        SyncItem[] result = new SyncItem[objects.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new SyncItem((Map) objects[i]);
        }
        return result;
    }

}
