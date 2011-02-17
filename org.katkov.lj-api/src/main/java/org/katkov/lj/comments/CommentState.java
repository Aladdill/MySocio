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

package org.katkov.lj.comments;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommentState {
    public static final CommentState SCREENED_COMMENT = new CommentState("S", "screened comment");
    public static final CommentState DELETED_COMMENT = new CommentState("D", "deleted comment");
    public static final CommentState ACTIVE_COMMENT = new CommentState("A", "active (visible) comment");
    public static final List<CommentState> ALL = Collections.unmodifiableList(Arrays.asList(new CommentState[]{SCREENED_COMMENT, DELETED_COMMENT, ACTIVE_COMMENT}));

    private final String id;
    private final String desc;

    public CommentState(String c, String desc) {
        id = c;
        this.desc = desc;
    }

    public static CommentState getInstance(String s) {
        for (CommentState state : ALL) {
            if (state.id.equals(s)) {
                return state;
            }
        }
        return null;
    }


    public String toString() {
        return "CommentState{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
