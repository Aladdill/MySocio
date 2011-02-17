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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * A class of convienience methods used throughout the toolkit.
 * 
 * @author Troy Bourdon
 *
 */
public class LiveJournalUtils {

	/**
	 * This method parses the Live Journal flat response on newline characters
	 * as per the flat protocol and stores the keys and values in a map.
	 * 
	 * @param response The flat response from Live Journal
	 * @return Map of response keys value pairs
	 */
	public static Map<String, String> convertFlatResponseToMap(String response) {
		Map<String, String> responseMap = new HashMap<String, String>();
		String[] responseArray = StringUtils.split(response, '\n');
		List<String> keyList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		
		for(int i = 0; i < responseArray.length; i++) {
			if(i == 0 || i % 2 == 0) 
				keyList.add(responseArray[i]);
			else
				valueList.add(responseArray[i]);
		}
		
		for(int j = 0; j < keyList.size(); j++) {
			responseMap.put(keyList.get(j), valueList.get(j));
		}

		return responseMap;		
	}
	
	/**
	 * Converts a number in string format to a java.util.Date
	 * 
	 * @param seconds The String representation of seconds
	 * @return The Date representation of the number
	 * @see Date
	 */
	public static Date toDate(String seconds) {
		long timeInSeconds = NumberUtils.toLong(seconds);
		Date date = new Date(timeInSeconds * 1000L);
		
		return date;
	}
	
	/**
	 * Parses a date string of the format yyyy-MM-dd hh:mm:ss to an actual
	 * Date type
	 * 
	 * @param date The date string of the format yyyy-MM-dd hh:mm:ss
	 * @return The Date representation of the date string
	 */
	public static Date toDateFromDateString(String date) {
		Date rtnDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		try {
			rtnDate = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return rtnDate;
	}
			
	/**
	 * Creates a date string of the format yyyy-MM-dd hh:mm:ss from the
	 * Date object.
	 * 
	 * @param date The Date object for converstion
	 * @return The String representation of the date
	 */
	public static String toDateStringFromDate(Date date) {
		String dateStr;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		dateStr = formatter.format(date);
		
		return dateStr;
	}	
	
	/**
	 * Parses a Date object and returns a year string.
	 * 
	 * @param date The Date object to be parsed
	 * @return The year string
	 */
	public static String toYearStringFromDate(Date date) {
		String year;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

		year = formatter.format(date);
		
		return year;		
	}

	/**
	 * Parses a Date object and returns a month string.
	 * 
	 * @param date The Date object to be parsed
	 * @return The month string
	 */
	public static String toMonthStringFromDate(Date date) {
		String month;
		SimpleDateFormat formatter = new SimpleDateFormat("MM");

		month = formatter.format(date);
		
		return month;				
	}

	/**
	 * Parses a Date object and returns a day string.
	 * 
	 * @param date The Date object to be parsed
	 * @return The day string
	 */
	public static String toDayStringFromDate(Date date) {
		String day;
		SimpleDateFormat formatter = new SimpleDateFormat("dd");

		day = formatter.format(date);
		
		return day;				
	}

	/**
	 * Parses a Date object and returns a hour string.
	 * 
	 * @param date The Date object to be parsed
	 * @return The hour string
	 */
	public static String toHourStringFromDate(Date date) {
		String hour;
		SimpleDateFormat formatter = new SimpleDateFormat("hh");

		hour = formatter.format(date);
		
		return hour;				
	}

	/**
	 * Parses a Date object and returns a minute string.
	 * 
	 * @param date The Date object to be parsed
	 * @return The minute string
	 */
	public static String toMinStringFromDate(Date date) {
		String minute;
		SimpleDateFormat formatter = new SimpleDateFormat("mm");

		minute = formatter.format(date);
		
		return minute;				
	}

}
