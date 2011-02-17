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

package org.ljtoolkit.response;

/**
 * This class represents common response attributes returned from a service call.
 * 
 * @author Troy Bourdon
 *
 */
class BaseResponse {
	private String success;
	private String errMsg;
	
	/**
	 * Returns the success string returned from the Live Journal service call.
	 * 
	 * @return success
	 */
	public String getSuccess() {
		return success;
	}
	
	/**
	 * Sets the success string returned from the Live Journal service call.
	 * @param success
	 */
	public void setSuccess(String success) {
		this.success = success;
	}
	
	/**
	 * Returns the error message returned from the Live Journal service call.
	 * 
	 * @return errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}
	
	/**
	 * Sets the error message returned from the Live Journal service call.
	 * 
	 * @param errMsg
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
