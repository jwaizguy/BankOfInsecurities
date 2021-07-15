package com.command;

import com.dao.SessionDataDAO;
import com.orm.SessionData;
import com.utility.TimeUtilityFunctions;

public class RegisterLoginCommand implements Command {
	private final SessionDataDAO sessionDataDAO;
	private final String userName;
	private final String sessionID;
	private final TimeUtilityFunctions timeUtilityFunctions;
	private SessionData outputSessionData;
	
	public RegisterLoginCommand(String sessionID, String userName, SessionDataDAO sessionDataDAO) {
		this.sessionDataDAO = sessionDataDAO;
		this.sessionID = sessionID;
		this.userName = userName;
		
		this.timeUtilityFunctions = new TimeUtilityFunctions();
	}

	@Override
	public void execute() {
		SessionData sessionData = new SessionData(this.sessionID, this.userName, this.timeUtilityFunctions.getCurrentTime());
		this.sessionDataDAO.addSessionData(sessionData);
		this.outputSessionData = sessionData;
	}

	@Override
	public void undo() {
		this.sessionDataDAO.removeSessionData(this.outputSessionData);
	}
	
	
}
