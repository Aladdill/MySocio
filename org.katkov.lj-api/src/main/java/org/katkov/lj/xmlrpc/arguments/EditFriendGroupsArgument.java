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

import java.util.HashMap;
import java.util.Map;

public class EditFriendGroupsArgument extends BaseArgument {


    /**
     * @param groupmasks
     * @@optional A structure of friend userids. The values of each are a string representing an unsigned 32-bit integer with bit 0 set (or the server will force it on anyway), bits 1-30 set for each group the friend belongs to, and bit 31 unset (reserved for future use).
     */
    public void setGroupMasks(int groupmasks) {
        struct.put("groupmasks", groupmasks);
    }

    /**
     * @param groups Given the bit of a friend group, the value contains a structure of information on it.
     * @@optional
     */
    public void set(EditFriendGroupsArgument.Group[] groups) {
        Map<Integer, Group> map = new HashMap<Integer, Group>(groups.length);
        for (int i = 0; i < groups.length; i++) {
            Group group = groups[i];
            map.put(i + 1, group);
        }
        struct.put("set", map);
    }

    /**
     * @param groupId A number of a friend group to delete (which can be from 1-30, inclusive). The server will modify all old entries that allow access to that friend group, so a new friend group using that number won't have access to old non-related entries, and unset the bit for that friend group on the groupmask of each friend, unless your client sends the friend's new groupmask explicitly.
     * @@optional
     */
    public void delete(int[] groupId) {
        struct.put("delete", groupId);
    }

    public static class Group extends HashMap {

        public Group() {
        }

        /**
         * @param name     Create or rename the friend group by sending this key. The value is the name of the group.
         * @param sort     This field should be sent to indicate the sorting order of this group. The value must be in the range of 0-255. The default is 50.
         * @param isPublic if true then this group is marked as public. If public, other users can see the name of the group and the people that are in it.
         */
        public Group(String name, int sort, boolean isPublic) {
            if (sort < 0 || sort > 255) {
                throw new IllegalArgumentException("Sort value must be in the range of 0-255");
            }
            put("name", name);
            put("sort", sort);
            put("public", isPublic ? 1 : 0);
        }


        public void setName(String name) {
            put("name", name);
        }

        public void setSort(int sort) {
            put("sort", sort);
        }

        public void setPublic(boolean value) {
            put("public", value ? 1 : 0);
        }
    }
}
