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

package org.ljtoolkit.params;

import java.util.Date;


public class BaseEventParams extends BaseParams {
	private Date timestamp;
	private String lineEndings;
	
	public static final String UNIX_LINE_ENDING = "\n";
	public static final String PC_LINE_ENDING = "\r\n";
	public static final String MAC_LINE_ENDING = "\r";

	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * Get the type of line-endings you're using. Possible values are unix (0x0A (\n)), pc (0x0D0A (\r\n)),
	 * or mac (0x0D (\r)). The default is not-Mac. Internally, LiveJournal stores all text as Unix-formatted text,
	 * and it does the conversion by removing all \r characters. If you're sending a multi-line event on Mac,
	 * you have to be sure and send a lineendings value of mac or your line endings will be removed. 
	 * PC and Unix clients can ignore this setting, or you can send it. It may be used for something more in the future.
	 * 
	 * @return String
	 */
	public String getLineEndings() {
		return lineEndings;
	}
	
	/**
	 * Set the line ending type.
	 * 
	 * @param lineEndings
	 */
	public void setLineEndings(String lineEndings) {
		this.lineEndings = lineEndings;
	}
}
