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

package org.ljtoolkit.client;

import java.text.SimpleDateFormat;
import java.util.List;

import org.ljtoolkit.data.Event;
import org.ljtoolkit.enums.EventSecurity;

/**
 * The purpose of this class is to support output of the Live Journal Client list events
 * option. A list of the user's Live Journal events are pretty printed to stdout.
 * 
 * @author Troy Bourdon
 *
 */
public class LJClientEventWriter {
	private static LJClientEventWriter instance;
	
	private LJClientEventWriter() {}
	
	public static LJClientEventWriter getInstance() {
		return (instance == null) ? new LJClientEventWriter() : instance;
	}
	
	public void outputLiveJournalEvents(List<Event> events) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("|  ID  |    Event Date    | Privacy |  Subject");
		System.out.println("--------------------------------------------------------------------------------");
		
		for(Event event : events) {
			String dateStr = dateFormat.format(event.getDate());
			Integer id = event.getId();
			String subject = event.getSubject();
						
			StringBuffer outputBuffer = new StringBuffer();
			outputBuffer.append("| " + String.format("%4d", id.intValue()) + " |");
			outputBuffer.append(" " + dateStr + " |");
			outputBuffer.append(" " + buildSecurityString(event.getSecurity()) + " |");
			outputBuffer.append(" " + subject);
			
			System.out.println(outputBuffer.toString());
		}
	}
	
	private String buildSecurityString(EventSecurity security) {
		return (security.equals(EventSecurity.PRIVATE)) ? "Private" :
			( (security.equals(EventSecurity.PUBLIC)) ? "Public " : "Friends");
	}
}
