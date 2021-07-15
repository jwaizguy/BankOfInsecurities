package com.builders;

public class AdminNavLink {
	private String displayText;
	private String linkToFile;
	
	public AdminNavLink(String displayText, String linkToFile) {
		this.displayText = displayText;
		this.linkToFile = linkToFile;
	}
	
	public String getDisplayText() {
		return this.displayText;
	}
	
	public String getLinkToFile() {
		return this.linkToFile;
	}
}
