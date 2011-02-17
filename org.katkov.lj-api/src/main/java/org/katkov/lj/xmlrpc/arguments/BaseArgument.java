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

import java.util.*;

public class BaseArgument implements Map {
    protected Map<String, Object> struct = new HashMap<String, Object>();


    public BaseArgument() {
        setVer(1);
    }

    /**
     * @param username Username of user logging results.
     * @@required
     */
    public void setUsername(String username) {
        struct.put("username", username);
    }

    /**
     * @param hpassword MD5 digest of user's password.
     * @@optional Not much more secure than password, but at least it's not results plain text.
     */
    public void setHpassword(String hpassword) {
        struct.put("hpassword", LJHelpers.MD5Encode(hpassword));
    }

    /**
     * @param password Password of user logging results results plaintext. If using the "clear" authentication method, either this or "hpassword" must be present.
     *                 deprecated
     * @@optional
     */
    public void setPassword(String password) {
        struct.put("password", password);
    }

    /**
     * @param auth_method Authentication method used for this request. The default value is "clear", for plain-text authentication. "cookie" and any of the challenge / response methods are also acceptable.
     * @@optional
     */
    public void setAuth_method(String auth_method) {
        struct.put("auth_method", auth_method);
    }

    /**
     * @param auth_challenge if using challenge / response authentication, this should be the challenge that was issued to you by the server.
     * @@optional
     */
    public void setAuth_challenge(String auth_challenge) {
        struct.put("auth_challenge", auth_challenge);
    }

    /**
     * @param auth_response If using challenge / response authentication, this should be the response hash that you generate, based on the formula required for your challenge.
     * @@optional
     */
    public void setAuth_response(String auth_response) {
        struct.put("auth_response", auth_response);
    }


    /**
     * @param ver Protocol version supported by the client; assumed to be 0 if not specified.
     * @@optional
     */
    public void setVer(int ver) {
        struct.put("ver", ver);
    }

    public void stripNullValues() {
        for (Iterator iterator = keySet().iterator(); iterator.hasNext();) {
            if (struct.get(iterator.next()) == null) {
                iterator.remove();
            }
        }
    }


    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Object key : keySet()) {
            Object value = struct.get(key);
            if (value != null) {
                buffer.append(key).append("->").append(value).append(", ");
            }
        }
        return buffer.toString();
    }

    //------------------------------------------------------------------
    //         delegation
    //------------------------------------------------------------------
    public int size() {
        return struct.size();
    }

    public boolean isEmpty() {
        return struct.isEmpty();
    }

    public boolean containsKey(Object key) {
        return struct.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return struct.containsValue(value);
    }

    public Object get(Object key) {
        return struct.get(key);
    }

    public Object put(Object key, Object value) {
        return struct.put((String) key, value);
    }

    public Object remove(Object key) {
        return struct.remove(key);
    }

    public void putAll(Map t) {
        struct.putAll(t);
    }

    public void clear() {
        struct.clear();
    }

    public Set keySet() {
        return struct.keySet();
    }

    public Collection values() {
        return struct.values();
    }

    public Set entrySet() {
        return struct.entrySet();
    }
}
