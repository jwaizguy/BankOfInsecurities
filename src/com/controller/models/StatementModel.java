package com.controller.models;

/** This datastructure is used to aggregate the amount of money despoited/withdrawn per month in the statement tab **/
public class StatementModel {
	private int month;
	private double amountWithdrawn;
	private double amountDeposited;
	
	public StatementModel(int month, double amountWithdrawn, double amountDeposited) {
		this.month = month;
		this.amountWithdrawn = amountWithdrawn;
		this.amountDeposited = amountDeposited;
	}
	
	public StatementModel(int month) { 
		this.month = month;
		this.amountDeposited = 0.0;
		this.amountWithdrawn = 0.0;
	}

	/* months are indexed 0-11 */
	public int getMonth() {
		return this.month;
	}
	
	public double getAmountWithdrawn() {
		return this.amountWithdrawn;
	}
	
	public void addAmountWithdrawn(double amountWithdrawn) {
		this.amountWithdrawn += Math.abs(amountWithdrawn);
	}
	
	public double getAmountDepositied() {
		return this.amountDeposited;
	}
	
	public void addAmountDeposited(double amountDeposited) {
		this.amountDeposited += Math.abs(amountDeposited);
	}
	
	public double getProfit() {
		return this.amountDeposited - this.amountWithdrawn;
	}
}
