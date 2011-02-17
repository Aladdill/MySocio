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

package org.ljtoolkit.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * This is a utility class used for Live Journal's challenge/response authentication.
 * 
 * @author Troy Bourdon
 *
 */
public class MD5 {
	
	/**
	 * This method serves as a utility for Live Journal's challange/response
	 * authentication scheme. The user's password is encoded where it is used
	 * in conjunction the the issued challenge for authentication.
	 * 
	 * @param text The user's unencoded password.
	 * @return The user's ecoded password.
	 */
	public static String getEncodedHex(String text) {
		MessageDigest md = null;
		String encodedString = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		Hex hex = new Hex();
		encodedString = new String(hex.encode(md.digest()));
		md.reset();
		return encodedString;
	}
}
