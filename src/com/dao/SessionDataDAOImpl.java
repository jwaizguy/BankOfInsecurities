package com.dao;

import java.util.HashSet;

import org.apache.log4j.Logger;

import com.orm.SessionData;

public class SessionDataDAOImpl implements SessionDataDAO {
	private HashSet<SessionData> sessionData;
	private static Logger logger = Logger.getLogger(SessionDataDAOImpl.class);
	
	public SessionDataDAOImpl() {
		this.sessionData = new HashSet<SessionData>();
	}
	
	@Override
	public boolean isValidCredentials(SessionData sessionData) {
		if(this.sessionData.contains(sessionData)) {
			logger.info("it is contained!");
			return true;
		} 
		logger.info("it is not contained! :( " + sessionData.toString());
		return false;
	}

	@Override
	public void addSessionData(SessionData sessionData) {
		logger.info("adding in: " + sessionData.toString());
		this.sessionData.add(sessionData);
	}

	@Override
	public void removeSessionData(SessionData sessionData) {
		this.sessionData.remove(sessionData);
	}

}
