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

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;

import java.util.ArrayList;
import java.util.List;

class LiveJournalProfileFriendsExtractor implements Extractor<String[]> {
    private String string;


    public LiveJournalProfileFriendsExtractor(String string) {
        this.string = string;
    }

    public String[] extract(String content) throws ParserException {
        Lexer lexer = new Lexer(content);
        Node node;
        List<String> result = new ArrayList<String>();
        while (null != (node = lexer.nextNode(false))) {
            if (node instanceof TextNode && node.toPlainTextString() != null && node.toPlainTextString().toUpperCase().indexOf(string.toUpperCase()) != -1) {
                break;
            }
        }
        while (null != (node = lexer.nextNode(false))) {
            if (node instanceof TextNode && node.toPlainTextString() != null && node.toPlainTextString().toUpperCase().indexOf("Account type".toUpperCase()) != -1) {
                break;
            }

            if (node instanceof TagNode &&
                    "a".equalsIgnoreCase(((TagNode) node).getTagName()) &&
                    !((TagNode) node).isEndTag() &&
                    ((TagNode) node).getAttribute("href") != null) {
                if (isProfileURL(node, "livejournal.com/profile") || isProfileURL(node, "users.livejournal.com/")) {
                    while (null != (node = lexer.nextNode(false))) {
                        if (node instanceof TextNode) {
                            result.add(node.toPlainTextString().trim());
                            break;
                        }
                    }
                } else if (result.size() > 0) {
                    break;
                }
            }

        }

        return result.toArray(new String[result.size()]);
    }

    private boolean isProfileURL(Node node, String subString) {
        return ((TagNode) node).getAttribute("href").toUpperCase().indexOf(subString.toUpperCase()) != -1;
    }
}
