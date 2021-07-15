package com.builders;

public class TableEntry {
	private String text;
	private boolean isHyperLink;
	private String hyperLink;
	private String htmlClass = "";
	
	public TableEntry(String text) {
		this.text = text;
		this.isHyperLink = false;
	}
	
	public TableEntry(String text, String hyperLink) {
		this.text = text;
		this.hyperLink = hyperLink;
		this.isHyperLink = true;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setHtmlClass(String htmlClass) {
		this.htmlClass = htmlClass;
	}
	
	public String getHtmlClass() {
		return this.htmlClass;
	}
	
	public boolean isHyperLink() {
		return this.isHyperLink;
	}
	
	public String getHyperLink() {
		return this.hyperLink;
	}
}
