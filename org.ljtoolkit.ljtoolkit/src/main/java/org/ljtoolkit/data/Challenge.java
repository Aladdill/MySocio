/*
 * Copyright 2008 Troy Bourdon
 * 
 * This file is part of LJToolkit.
 *
 * LJToolkit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LJToolkit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LJToolkit.  If not, see <http://www.gnu.org/licenses/>.    
 */

package org.ljtoolkit.data;

import java.util.Date;

/**
 * The Challenge class represents the returned response from the Live Journal server
 * on a getchallenge request.
 * 
 * @author Troy Bourdon
 *
 */
public class Challenge {
	private String challenge;
	private String success;
	private String errMsg;
	private String authScheme;
	private Date expireTime;
	private Date serverTime;
	
	/**
	 * Returns the challange string to generate a hashed response from.
	 * 
	 * @return challange string which is used to generate a hashed response
	 */
	public String getChallenge() {
		return challenge;
	}
	
	/**
	 * An opaque cookie to generate a hashed response from.
	 * 
	 * @param challenge
	 */	
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	
	/**
	 * OK on success or FAIL when there's an error. When there's an error, see errmsg for the error text.
	 * The absence of this variable should also be considered an error.
	 * 
	 * @return success status
	 */
	public String getSuccess() {
		return success;
	}
	
	/**
	 * Setter for the success attribute.
	 * 
	 * @param success status
	 */
	public void setSuccess(String success) {
		this.success = success;
	}
	
	/**
	 * The error message if success was FAIL, not present if OK. If the success variable isn't present,
	 * this variable most likely won't be either (in the case of a server error), and clients should 
	 * just report "Server Error, try again later.
	 * 
	 * @return error message
	 */
	public String getErrMsg() {
		return errMsg;
	}
	
	/**
	 * Setter for the errMsg attribute.
	 * 
	 * @param errMsg error message
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	/**
	 * You can ignore this for now. By default this is the highest version of our authentication schemes,
	 * if in the future Live Journal implements other auth schemes or change the default. In that case LJ
	 * would add a new capabilities exchange: your client could say, "I know c0 and c1", and our server 
	 * would then say, "Use c1, it's the best."
	 * 
	 * @return authentication scheme
	 */
	public String getAuthScheme() {
		return authScheme;
	}
	
	/**
	 * This method will be used internally to set the authentication scheme of the challenge
	 * as returned from a call to the {@link org.ljtoolkit.services.LiveJournalService} getChallenge method.
	 * 
	 * @param authScheme The authentication scheme
	 */
	public void setAuthScheme(String authScheme) {
		this.authScheme = authScheme;
	}
	
	/**
	 * The expiration time of the challenge, as measured in seconds since the Unix epoch.
	 * @return expiration time.
	 */
	public Date getExpireTime() {
		return expireTime;
	}
	
	/**
	 * This method will be used internally to set the expiration time of the challenge as
	 * returned from a call to the {@link org.ljtoolkit.services.LiveJournalService} getChallenge method.
	 * 
	 * @param expireTime The expiration time
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	/**
	 * The server time when the challenge was generated, as measured in seconds since the Unix epoch.
	 * 
	 * @return date/time of the Live Journal server
	 */
	public Date getServerTime() {
		return serverTime;
	}
	
	/**
	 * This method will be used internally to set the time of the Live Journal server which
	 * was returned from a call to the {@link org.ljtoolkit.services.LiveJournalService} getChallenge method.
	 * 
	 * @param serverTime The Live Journal server time.
	 */
	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}
}
