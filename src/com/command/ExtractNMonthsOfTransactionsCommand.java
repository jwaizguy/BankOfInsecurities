package com.command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.controller.models.StatementModel;
import com.orm.Transaction;
import com.utility.TimeUtilityFunctions;

/** Instead of just displaying a history of every month's transaction, I am going to aggregate it into the last 'n' months into a hashmap **/
public class ExtractNMonthsOfTransactionsCommand implements Command {
	private TimeUtilityFunctions timeUtilities;
	private List<Transaction> transactions;
	private int numberOfMonths;
	private final int outsideOfRangeIndex = -1;
	private List<StatementModel> statementStats;
	
	private static Logger logger = Logger.getLogger(ExtractNMonthsOfTransactionsCommand.class);
	
	public ExtractNMonthsOfTransactionsCommand(List<Transaction> transactions, int numberOfMonths) {
		this.transactions = transactions;
		this.numberOfMonths = numberOfMonths;
		
		this.timeUtilities = new TimeUtilityFunctions();
	}

	@Override
	public void execute() {
		HashMap<Integer, List<Transaction> > monthOfTransactions = this.groupTransactionsByMonth(this.transactions);
		this.statementStats = this.aggregateIntoStatementModel(monthOfTransactions);
	}
	
	public List<StatementModel> getStatementStats() {
		List<StatementModel> output = new ArrayList<StatementModel>();
		
		for(int i = 0; i < this.statementStats.size(); i++) {
			if(this.statementStats.get(i).getMonth() == this.outsideOfRangeIndex) {
				continue;
			} else {
				output.add(this.statementStats.get(i));
			}
		}
		
		return output;
	}
	
	public int getOutOfRangeStatisticsIndex() {
		return this.outsideOfRangeIndex;
	}
	
	private List<StatementModel> aggregateIntoStatementModel(HashMap<Integer, List<Transaction> > monthOfTransactions) {
		List<StatementModel> output = new ArrayList<StatementModel>();
		
		for(int i = this.outsideOfRangeIndex; i < this.numberOfMonths ; i++) {
			List<Transaction> inputs = monthOfTransactions.get(i);
			StatementModel statementModel = new StatementModel(i);
			
			if (inputs != null) {
				for(Transaction transaction : inputs) {
					double transactionAmount = transaction.getTransactionAmount();
					if(transaction.getTransactionAmount() > 0) {
						statementModel.addAmountDeposited(transactionAmount);
					} else {
						statementModel.addAmountWithdrawn(transactionAmount);
					}
				}
			}
			output.add(statementModel);
		}
		return output;
	}
	
	private HashMap<Integer, List<Transaction> > initMonthsOfTransactionsMap(int numMonths) {
		HashMap<Integer, List<Transaction> > monthOfTransactions = new HashMap<Integer, List<Transaction> >();
		for(int i = this.outsideOfRangeIndex; i < this.numberOfMonths; i++) {
			monthOfTransactions.put(i, new ArrayList<Transaction>());
		}
		
		return monthOfTransactions;
	}
	
	private HashMap<Integer, List<Transaction> > groupTransactionsByMonth(List<Transaction> transactions) {
		Calendar currentTime = this.timeUtilities.getCurrentTime();
		HashMap<Integer, List<Transaction> > monthOfTransactions = this.initMonthsOfTransactionsMap(this.numberOfMonths);
		
		for(Transaction transaction : transactions) {
			Calendar transactionTime = this.timeUtilities.timeStampToCalendar(transaction.getTransactionTime());
			int monthsSinceCurrentTime = this.timeUtilities.monthsSinceInputTime(currentTime, transactionTime);
			
			if(monthsSinceCurrentTime < this.numberOfMonths) {
				List<Transaction> timeSlotTransactions = monthOfTransactions.get(monthsSinceCurrentTime);
				timeSlotTransactions.add(transaction);
				monthOfTransactions.put(monthsSinceCurrentTime, timeSlotTransactions);
			} else {
				List<Transaction> timeSlotTransactions = monthOfTransactions.get(this.outsideOfRangeIndex);
				timeSlotTransactions.add(transaction);
				monthOfTransactions.put(this.outsideOfRangeIndex, timeSlotTransactions);
			}
		}
		
		return monthOfTransactions;
	}
 
	@Override
	public void undo() { }

}
