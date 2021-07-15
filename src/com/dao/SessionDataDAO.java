package com.dao;

import com.orm.SessionData;

public interface SessionDataDAO {
	public boolean isValidCredentials(SessionData sessionData);
	public void addSessionData(SessionData sessionData);
	public void removeSessionData(SessionData sessionData);
}
