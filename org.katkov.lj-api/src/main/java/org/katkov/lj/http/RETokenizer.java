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

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RETokenizer implements Iterator {
    // Holds the original input to search for tokens
    private CharSequence input;

    // Used to find tokens
    private Matcher matcher;

    // If true, the String between tokens are returned
    private boolean returnDelims;

    // The current delimiter value. If non-null, should be returned
    // at the next call to next()
    private String delim;

    // The current matched value. If non-null and delim=null,
    // should be returned at the next call to next()
    private String match;

    // The value of matcher.end() from the last successful match.
    private int lastEnd = 0;
    private int group;

    // patternStr is a regular expression pattern that identifies tokens.
    // If returnDelims delim is false, only those tokens that match the
    // pattern are returned. If returnDelims true, the text between
    // matching tokens are also returned. If returnDelims is true, the
    // tokens are returned in the following sequence - delimiter, token,
    // delimiter, token, etc. Tokens can never be empty but delimiters might
    // be empty (empty string).
    public RETokenizer(CharSequence input, String patternStr, boolean returnDelims, int group) {
        this.group = group;
        // Save values
        this.input = input;
        this.returnDelims = returnDelims;

        // Compile pattern and prepare input
        Pattern pattern = Pattern.compile(patternStr);
        matcher = pattern.matcher(input);
    }

    // Returns true if there are more tokens or delimiters.
    public boolean hasNext() {
        if (matcher == null) {
            return false;
        }
        if (delim != null || match != null) {
            return true;
        }
        if (matcher.find()) {
            if (returnDelims) {
                delim = input.subSequence(lastEnd, matcher.start()).toString();
            }
            match = matcher.group(group);
            lastEnd = matcher.end();
        } else if (returnDelims && lastEnd < input.length()) {
            delim = input.subSequence(lastEnd, input.length()).toString();
            lastEnd = input.length();

            // Need to remove the matcher since it appears to automatically
            // reset itself once it reaches the end.
            matcher = null;
        }
        return delim != null || match != null;
    }

    // Returns the next token (or delimiter if returnDelims is true).
    public Object next() {
        String result = null;

        if (delim != null) {
            result = delim;
            delim = null;
        } else if (match != null) {
            result = match;
            match = null;
        }
        return result;
    }

    // Returns true if the call to next() will return a token rather
    // than a delimiter.
    public boolean isNextToken() {
        return delim == null && match != null;
    }

    // Not supported.
    public void remove() {
        throw new UnsupportedOperationException();
    }
}