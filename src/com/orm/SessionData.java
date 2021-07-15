package com.orm;

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.utility.TimeUtilityFunctions;

public class SessionData {
	private String sessionID;
	private String username;
	private Calendar loginTime;
	
	private static Logger logger = Logger.getLogger(SessionData.class);
	
	public SessionData(String sessionID, String username) {
		this.sessionID = sessionID;
		this.username = username;
		
		TimeUtilityFunctions timeUtility = new TimeUtilityFunctions();
		this.loginTime = timeUtility.getCurrentTime();
	}
	
	public SessionData(String sessionID, String username, Calendar loginTime) {
		this.sessionID = sessionID;
		this.username = username;
		this.loginTime = loginTime;
	}
	
	public String getSessionID() {
		return this.sessionID;
	}
	
	public String getUserName() {
		return this.username;
	}
	
	public Calendar getLoginTime() {
		return this.loginTime;
	}
	
	@Override
	public int hashCode() {
		return this.sessionID.hashCode();
	}
	
	@Override
	public String toString() {
		return "< " + this.getUserName() + " " + this.getSessionID() + " >";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SessionData)) {
			return false;
		} else if(obj == this) {
			return true;
		}
		
		SessionData sessionData = (SessionData) obj;
		logger.info(this.toString() + " " + obj.toString());
		if(sessionData.getSessionID().equals(this.getSessionID()) && sessionData.getUserName().equals(this.getUserName())) {
			return true;
		}
		return false;
	}
}
