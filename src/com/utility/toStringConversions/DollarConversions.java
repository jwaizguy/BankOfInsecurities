package com.utility.toStringConversions;
import java.text.NumberFormat;

public class DollarConversions {
	public DollarConversions() { } 
	
	public enum doubleOptions {normal, positive, negative};
	
	
	public String convertDoubleToType(DollarConversions.doubleOptions type, double number) {
		NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
		String output = defaultFormat.format(number);
		output = output.replaceAll("[()]", "");
		
		if(type.equals(DollarConversions.doubleOptions.normal)) {
			output = this.getRegularDollarAmount(output, number);
		} else if(type.equals(DollarConversions.doubleOptions.positive)) {
			output = this.getPositiveDollarAmount(output);
		} else if(type.equals(DollarConversions.doubleOptions.negative)) {
			output = this.getNegativeDollarAmount(output);
		}
		
		return output;
	}
	
	private String getRegularDollarAmount(String output, final double input) {
		if(input < 0) {
			return this.getNegativeDollarAmount(output);
		}
		return output;
	}
	
	private String getNegativeDollarAmount(String output) {
		output = "-" + output;
		return output;
	}
	
	private String getPositiveDollarAmount(String output) {
		output = "+" + output;
		return output;
	}
}
