package com.builders;

import java.util.HashMap;

/** This class can be used for testing or generating placeholder values until the DB connection stuff is finished **/
public class RandomDataGenerator {
	private HashMap<Integer, Integer> daysInMonthMap;
	
	public String randomTime() {
		String amOrPm = " AM";
		int num = this.randomInteger(0, 1);
		if(num == 0) {
			amOrPm = " PM";
		}
		
		return this.randomInteger(1, 12).toString() + ":" + this.randomInteger(0, 59).toString() + amOrPm;
	}
	
	public String randomLabelWord() {
		String[] wordSet = {"Date", "Time", "From", "To", "Amount", "Quality", "Quantity", "Growth", "Loss", "Profit", "Accumulation", "Revenue"};
		
		return wordSet[this.randomInteger(0, wordSet.length - 1)];
	}
	
	public String randomDate() {
		if(this.daysInMonthMap == null) { this.initDaysInMonthMap(); }
		
		int month = this.randomInteger(1, 12);
		return month + "/" + this.randomInteger(1, this.daysInMonthMap.get(month));
	}
	
	public String randomDollarAmount(int min, int max) {
		return "$" + this.randomInteger(min, max).toString();
	}
	
	public Integer randomInteger(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	private void initDaysInMonthMap() {
		this.daysInMonthMap = new HashMap<Integer, Integer>();
	
		for(int month = 1; month <= 12; month++) {
			if(month == 2) {
				this.daysInMonthMap.put(month, 28);
			} else if(month == 4 || month == 6 || month == 9 || month == 11) {
				this.daysInMonthMap.put(month, 30);
			} else {
				this.daysInMonthMap.put(month, 31);
			}
		}
	}
}
