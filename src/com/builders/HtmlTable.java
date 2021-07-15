package com.builders;

import org.apache.log4j.Logger;

import com.controller.TransactionHistoryController;

public class HtmlTable {
	private TableEntry[][] outputTable;
	private int rows;
	private int cols;
	private String tableClass;
	private Integer width;
	private Integer borderType;
	
	private static Logger logger = Logger.getLogger(TransactionHistoryController.class);
	
	public HtmlTable(AbstractTableBuilder tableBuilder) {
		this.tableClass = tableBuilder.getTableClass();
		this.outputTable = tableBuilder.getFullTable();
		this.rows = this.outputTable.length;
		this.cols = this.outputTable[0].length;
		this.width = tableBuilder.getTableWidth();
		this.borderType = tableBuilder.getBorderType();
	}
	
	public HtmlTable(int rows, int cols, String tableClass, Integer width, Integer borderType, boolean isRandomized) {
		this.tableClass = tableClass;
		this.rows = rows;
		this.cols = cols;
		this.width = width;
		this.borderType = borderType;
		
		this.outputTable = new TableEntry[rows][cols];
		
		if(isRandomized) {
			this.randomlyPopulateTable();
		}
	}
	
	public void addHeaderToTable(String[] inputHeader) {
		for(int i = 0; i < inputHeader.length; i++) {
			this.outputTable[0][i] = new TableEntry(inputHeader[i]);
		}
	}
	
	private void randomlyPopulateTable() {
		RandomDataGenerator randomizer = new RandomDataGenerator();
		
		for(int r = 0; r < this.rows; r++) {
			for(int c = 0; c < this.cols; c++) {
				this.outputTable[r][c] = new TableEntry(randomizer.randomDollarAmount(25, 50));
			}
		}
		
		for(int c = 0; c < this.cols; c++) {
			this.outputTable[0][c] = new TableEntry(randomizer.randomLabelWord());
		}
	}
	
	public TableEntry getValueAt(int r, int c) {
		return this.outputTable[r][c];
	}
	
	public void setValue(TableEntry value, int r, int c) {
		outputTable[r][c] = value;
	}
	
	public int getNumberOfColumns() {
		return this.cols;
	}
	
	public int getNumberOfRows() {
		return this.rows;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public String getBorderType() {
		return this.borderType.toString();
	}
	
	public String getTableClass() {
		return this.tableClass;
	}
	
	public TableEntry[][] getOutputTable() {
		return this.outputTable;
	}
}
