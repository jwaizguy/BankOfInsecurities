package com.builders;

public abstract class AbstractTableBuilder implements TableBuilder {
	protected final String defaultClass = "center";
	protected final int defaultWidth = 300;
	protected final int defaultBorder = 1;
	
	protected String tableClass = defaultClass;
	protected int tableWidth = defaultWidth;
	protected int tableBorderType = defaultBorder;
	protected TableEntry[][] fullTable;
	protected TableEntry[][] headerlessTable;
	
	protected void createFullTable(TableEntry[] headers, TableEntry[][] body) {
		// creating the output table, adding one for the headers to be included in the rows
		TableEntry[][] output = new TableEntry[body.length + 1][headers.length];
		
		for(int r = 0; r < output.length; r++) {
			for(int c = 0; c < output[0].length; c++) {
				if(r == 0) {
					output[r][c] = headers[c];
				} else {
					output[r][c] = body[r - 1][c];
				}
			}
		}
		this.fullTable = output;
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for(int r = 0; r < this.fullTable.length; r++) {
			for(int c = 0; c < this.fullTable[0].length; c++) {
				stringBuilder.append(this.fullTable[r][c].getText() + " ");
			}
			stringBuilder.append("\n");
		}
		
		return stringBuilder.toString();
	}
	
	/* The full table is the combination of the headers and the body of the original table into one table. */
	public TableEntry[][] getFullTable() {
		return this.fullTable;
	}
	
	public String getTableClass() {
		return this.tableClass;
	}
	
	public int getTableWidth() {
		return this.tableWidth;
	}
	
	public int getBorderType() {
		return this.tableBorderType;
	}
}
