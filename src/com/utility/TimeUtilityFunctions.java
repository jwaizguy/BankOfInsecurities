package com.utility;

import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class TimeUtilityFunctions {
	private static Logger logger = Logger.getLogger(TimeUtilityFunctions.class);
	
	
	public int getYearOfNMonthsBeforeToday(int n) {
		Calendar currentTime = this.getCurrentTime();
		return this.getYearOfNMonthsBeforeDate(currentTime, n);		
	}
	
	public int getYearOfNMonthsBeforeDate(Calendar date, int n) {
		int numberOfYearsAgo = Math.floorDiv(n, 12);
		int remainingMonths = n % 12;
		
		int currentMonth = date.get(Calendar.MONTH);
		
		if(currentMonth - remainingMonths < 0) {
			numberOfYearsAgo += 1;
		}
		
		return date.get(Calendar.YEAR) - numberOfYearsAgo;
	}
	
	public int getNthMonthBeforeToday(int n) {
		Calendar currentTime = this.getCurrentTime();
		
		return this.nonNegativeModulo((currentTime.get(Calendar.MONTH) - this.nonNegativeModulo(n, 12)), 12);
	}
	
	private int nonNegativeModulo(int a, int b) {
		return (a % b + b) % b;
	}
	
	public Calendar timeStampToCalendar(Timestamp timestamp) {
		Calendar output = Calendar.getInstance();
		output.setTime(timestamp);
		
		return output;
	}
	
	public Calendar getCurrentTime() {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Calendar calendar  = Calendar.getInstance();
		calendar.setTime(currentTime);
		
		return calendar;
	}
	
	public int monthsSinceInputTime(Calendar currentTime, Calendar inputTime) {
		int currentYear = currentTime.get(Calendar.YEAR);
		int currentMonth = currentTime.get(Calendar.MONTH);
		
		int inputYear = inputTime.get(Calendar.YEAR);
		int inputMonth = inputTime.get(Calendar.MONTH);
		
		int totalMonths = (12 * Math.abs(currentYear - inputYear)) + Math.abs(currentMonth - inputMonth);
		
		return totalMonths;
	}
}
