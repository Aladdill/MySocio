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

import java.util.ArrayList;
import java.util.List;

import org.ljtoolkit.utils.KeyValuePair;

/**
 * This class represents the parameters that are common to Live Journal protocol tasks.
 * It is extended by other parameter classes and is set as a package protected to prevent
 * instantiation elsewhere.
 * 
 * @author Troy Bourdon
 *
 */
class BaseParams {
	private String useJournal;
	private int version;
	private List<KeyValuePair> keyValues = new ArrayList<KeyValuePair>();
	
	public String getUseJournal() {
		return useJournal;
	}
	
	public void setUseJournal(String useJournal) {
		this.useJournal = useJournal;
	}

	/**
	 * Returns the protocol version number
	 * @return version
	 */
	public int getVersion() {
		return version;
	}
	
	/**
	 * (Optional) Protocol version supported by the client; assumed to be 0 if not specified. 
	 * 
	 * @param version
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	
	/**
	 * List of key/value pairs to set in the post parameters
	 * @return List-keyValues
	 */
	public List<KeyValuePair> getKeyValuePairs() {
		return keyValues;
	}
}
