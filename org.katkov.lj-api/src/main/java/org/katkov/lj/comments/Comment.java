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


import java.util.Date;

/**
 * Represent a comment
 */
public class Comment implements Comparable<Comment> {
    private CommentState state = CommentState.ACTIVE_COMMENT;

    private int id;
    private int posterid;
    private int jitemid;
    private int parentid;
    private String body;
    private String subject;
    private Date date;
    private String posterName;


    public Comment(int id, int posterid) {
        this.posterid = posterid;
        this.id = id;
    }

    public Comment(int id, int posterid, CommentState state, int jitemid, int parentid, String body, String subject, Date date) {
        this.id = id;
        this.posterid = posterid;
        this.state = state;
        this.jitemid = jitemid;
        this.parentid = parentid;
        this.body = body;
        this.subject = subject;
        this.date = date;
    }

    /**
     * The id of this particular comment.
     *
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * The id of the poster of this comment. This can only change from 0 (anonymous) to some non-zero number. It will never go the other way, nor will it change from some non-zero number to another non-zero number. Anonymous (0) is the default if no posterid is supplied.
     *
     * @return id
     */
    public int getPosterid() {
        return posterid;
    }

    /**
     * Can be screened comment, deleted comment, active (visible) comment. If the state is not explicitly defined, it is assumed to be active.
     *
     * @return comment state
     */
    public CommentState getState() {
        return state;
    }

    /**
     * Journal itemid this comment was posted in.
     *
     * @return id
     */
    public int getJitemid() {
        return jitemid;
    }

    /**
     * 0 if this comment is top-level, else, it is the id of the comment this one was posted in response to. Top-level (0) is the default if no parentid is supplied.
     *
     * @return id
     */
    public int getParentid() {
        return parentid;
    }

    /**
     * The body of the comment
     *
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * The subject of the comment
     *
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * The date of the comment
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Livejournal name (login) of the author
     *
     * @return name, null for anonymous
     */
    public String getPosterName() {
        return posterName;
    }


    void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public int compareTo(Comment comment) {
        return new Integer(getId()).compareTo(comment.getId());
    }


    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", posterid=" + posterid +
                ", state=" + state +
                ", jitemid=" + jitemid +
                ", parentid=" + parentid +
                ", body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                ", date=" + date +
                ", posterName='" + posterName + '\'' +
                '}';
    }
}


