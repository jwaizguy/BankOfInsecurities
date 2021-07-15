package com.command;

public class FragmentDecimalCommand implements PermanentCommand {
	private final double n;
	private int numPlaces;
	private double newValue;
	private double funValue;
	
	public FragmentDecimalCommand(double n, int numPlaces) {
		this.n = n;
		this.numPlaces = numPlaces;
	}

	@Override
	public void execute() {
		this.newValue = n;
		
		for(int i = 3; i <= this.numPlaces; i++) {
			double decimalValue = this.getNthPlacePastDecimal(this.n, i);
			this.newValue -= decimalValue;
			this.funValue += decimalValue;
		}
		
	}
	
	private double getNthPlacePastDecimal(double value, int nthPlace) {
		double multBy = Math.pow(10, nthPlace);
		value *= multBy;
		double output = Math.floor(value % 10);
		return output / multBy;
	}
	
	public double getNewValue() {
		return this.newValue;
	}
	
	public double getFunValue() {
		return this.funValue;
	}
	
}
