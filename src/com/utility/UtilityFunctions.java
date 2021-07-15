package com.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.constants.ControllerConstants;

public class UtilityFunctions
{
	// This method is used to generate a unique Transaction ID
	// for every transaction
	public static String getNewAndUniqueTransactionID()
	{
		StringBuilder sb = new StringBuilder("TX");
		
		// Want to get today's date in DDMMYYYY format
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY");
		sb.append(sdf.format(new Date()));
		
		sb.append(UtilityFunctions.generateRandomUUID());
		return sb.substring(0, 18);
	}
	
	public static  String generateRandomUUID()
	{
		String id = UUID.randomUUID().toString();
		return id.substring(0, 13);
	}
	
	// This method creates and adds an authentication cookie to response header
	public static void addAuthenticatedCookies(HttpServletResponse response, String userName, String sessionID)
	{
		Cookie authentication = new Cookie(ControllerConstants.LOGIN_COOKIE_NAME, userName);
		authentication.setMaxAge(ControllerConstants.MAX_COOKIE_DURATION_SECONDS);
		response.addCookie(authentication);
		
		Cookie sessionIDCookie = new Cookie(ControllerConstants.SESSION_ID_COOKIE_NAME, sessionID);
		sessionIDCookie.setMaxAge(ControllerConstants.MAX_COOKIE_DURATION_SECONDS);
		response.addCookie(sessionIDCookie);
	}
}
