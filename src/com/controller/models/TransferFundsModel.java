package com.controller.models;

public class TransferFundsModel {
	private double amount;
	private long toAccountNumber;
	
	public TransferFundsModel() { }
	
	public TransferFundsModel(double amount, long toAccountNumber) {
		this.amount = amount;
		this.toAccountNumber = toAccountNumber;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public void setToAccountNumber(long accountNumber) {
		this.toAccountNumber = accountNumber;
	}
	
	public long getToAccountNumber() {
		return this.toAccountNumber;
	}
}
