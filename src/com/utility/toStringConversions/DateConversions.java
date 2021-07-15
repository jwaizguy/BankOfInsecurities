package com.utility.toStringConversions;

import java.text.DateFormatSymbols;

public class DateConversions {
	
	/* Month ranges 0-11 to Jan-Dec */
	public String toShortDate(int month, int year) {
		String monthString = new DateFormatSymbols().getMonths()[month];
		monthString = monthString.substring(0, Math.min(monthString.length(), 3));
		
		String yearString = Integer.toString(year);
		
		return monthString + " " + yearString;
	}
}
