package com.builders;

import java.util.List;

import org.apache.log4j.Logger;

import com.command.ExtractNMonthsOfTransactionsCommand;
import com.controller.models.StatementModel;
import com.orm.Transaction;
import com.utility.TimeUtilityFunctions;
import com.utility.toStringConversions.DateConversions;
import com.utility.toStringConversions.DollarConversions;

public class StatementTableBuilder extends AbstractTableBuilder {
	private final TableEntry monthLabel = new TableEntry("Month");
	private final TableEntry depositLabel = new TableEntry("Deposited");
	private final TableEntry withdrawnLabel = new TableEntry("Withdrawn");
	private final TableEntry profitLabel = new TableEntry("Profit");
	
	private final TableEntry[] headerValues = {monthLabel, depositLabel, withdrawnLabel, profitLabel};
	
	private DateConversions dateConversions = new DateConversions();
	private DollarConversions dollarConversions = new DollarConversions();
	private TimeUtilityFunctions timeFunctions = new TimeUtilityFunctions();
	private final int numberOfMonthsToCareAbout = 12;
	private static Logger logger = Logger.getLogger(StatementTableBuilder.class);
	
	public StatementTableBuilder(final List<Transaction> accounts) {
		ExtractNMonthsOfTransactionsCommand statementModelExtractor = new ExtractNMonthsOfTransactionsCommand(accounts, this.numberOfMonthsToCareAbout);
		statementModelExtractor.execute();
		
		List<StatementModel> statementModels = statementModelExtractor.getStatementStats();
		this.headerlessTable = this.extractTableBody(statementModels);
		this.createFullTable(headerValues, headerlessTable);
		this.tableWidth *= 1.5;
	}
	
	private TableEntry[][] extractTableBody(final List<StatementModel> transactions) {
		TableEntry[][] outputTable = new TableEntry[transactions.size()][this.headerValues.length];
		for(int i = 0; i < transactions.size(); i++) {
			outputTable[i] = this.extractCurrentEntriesData(transactions.get(i));
		}
		
		return outputTable;
	}
	
	private TableEntry[] extractCurrentEntriesData(StatementModel transaction) {
		TableEntry[] output = new TableEntry[this.headerValues.length];
		
		for(int i = 0; i < this.headerValues.length; i++) {
			if(this.headerValues[i].equals(this.monthLabel)) {
				int monthNumber = this.timeFunctions.getNthMonthBeforeToday(transaction.getMonth());
				int yearNumber = this.timeFunctions.getYearOfNMonthsBeforeToday(transaction.getMonth());
				output[i] = new TableEntry(this.dateConversions.toShortDate(monthNumber, yearNumber));
			} else if(this.headerValues[i].equals(this.depositLabel)) {
				output[i] = new TableEntry(this.dollarConversions.convertDoubleToType(DollarConversions.doubleOptions.positive, transaction.getAmountDepositied()));
			} else if(this.headerValues[i].equals(this.withdrawnLabel)) {
				output[i] = new TableEntry(this.dollarConversions.convertDoubleToType(DollarConversions.doubleOptions.negative, transaction.getAmountWithdrawn()));
			} else if(this.headerValues[i].equals(this.profitLabel)) {
				output[i] = new TableEntry(this.dollarConversions.convertDoubleToType(DollarConversions.doubleOptions.normal, transaction.getProfit()));
			}
		}
		
		return output;
	}

	@Override
	public HtmlTable build() {
		return new HtmlTable(this);
	}

}
