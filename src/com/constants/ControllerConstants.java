package com.constants;

public class ControllerConstants {
	// it's actually resources/uploads/, but the resourcesloader doesn't need the resources part!
	public static final String UPLOADED_FILES_DIRECTORY = "uploads/";
	public static final String DEFAULT_UPLOADED_FILENAME = "defaultuploadfile.jpg";
	public static final String DEFAULT_SERVLET_UPLOAD_DIRECTORY = "/uploads/";
	
	public static final String LOGIN_COOKIE_NAME = "isLoggedIn";
	public static final String NULL_COOKIE = "Null Cookie";
	public static final String SESSION_ID_COOKIE_NAME = "sessionIdCookie";
	public static final String COOKIE_DOMAIN_STRING = "BankOfInsecurities/";
	public static final int MAX_COOKIE_DURATION_SECONDS = 60 * 60 * 3;
	public static final char ACCOUNT_IS_ACTIVE = 'T';
	public static final char ACCOUNT_IS_NOT_ACTIVE = 'F';
	
	// NOTE: it's a long.  The last character is lower-case 'L'
	public static final long JOHN_DOE_ACCOUNT_NUMBER = 5555555555l;
	
	public static final String HOME_DIRECTORY = "/";
	
	public static final String LOGIN_PAGE_NAME = "login";
	public static final String LOGIN_PAGE_DIRECTORY = HOME_DIRECTORY + LOGIN_PAGE_NAME;
	
	public static final String FORGOT_PASSWORD_PAGE_NAME = "forgotPassword";
	public static final String FORGOT_PASSWORD_PAGE_DIRECTORY = HOME_DIRECTORY + FORGOT_PASSWORD_PAGE_NAME;
	
	public static final String ACCOUNT_MANAGEMENT_PAGE_NAME = "accountManagement";
	public static final String ACCOUNT_MANAGEMENT_DIRECTORY = HOME_DIRECTORY + ACCOUNT_MANAGEMENT_PAGE_NAME;
	
	public static final String REDIRECT_PAGE_NAME = "redirect";
	public static final String REDIRECT_PAGE_DIRECTORY = HOME_DIRECTORY + REDIRECT_PAGE_NAME;
	
	public static final String SINGLE_ACCOUNT_MANAGEMENT_PAGE_NAME = "singleBankAccountInfo";
	public static final String SINGLE_ACCOUNT_MANAGEMENT_PAGE_DIRECTORY = HOME_DIRECTORY + SINGLE_ACCOUNT_MANAGEMENT_PAGE_NAME;
	
	public static final String TRANSACTION_HISTORY_PAGE_NAME = "transactionHistory";
	public static final String TRANSACTION_HISTORY_DIRECTORY = HOME_DIRECTORY + TRANSACTION_HISTORY_PAGE_NAME;
	
	public static final String CONTACT_INFORMATION_PAGE_NAME = "contactInformation";
	public static final String CONTACT_INFORMATION_DIRECTORY = HOME_DIRECTORY + CONTACT_INFORMATION_PAGE_NAME;
	
	public static final String PROFILE_PAGE_NAME = "profilePage";
	public static final String PROFILE_DIRECTORY = HOME_DIRECTORY + PROFILE_PAGE_NAME;
	
	public static final String SINGLE_PROFILE_PAGE_NAME = "singleProfilePage";
	public static final String SINGLE_PROFILE_PAGE_DIRECTORY = HOME_DIRECTORY + SINGLE_PROFILE_PAGE_NAME;
	
	public static final String STATEMENT_PAGE_NAME = "statement";
	public static final String STATEMENT_DIRECTORY = HOME_DIRECTORY + STATEMENT_PAGE_NAME;
	
	public static final String MESSAGE_PAGE_NAME = "messagePage";
	public static final String MESSAGE_PAGE_DIRECTORY = HOME_DIRECTORY + MESSAGE_PAGE_NAME;
	
	public static final String ADMIN_MESSAGE_PAGE_NAME = "adminMessageCenter";
	public static final String ADMIN_MESSAGE_DIRECTORY = HOME_DIRECTORY + ADMIN_MESSAGE_PAGE_NAME;
	
	public static final String ADMIN_DASHBOARD_PAGE_NAME = "adminDashboard";
	public static final String ADMIN_DASHBOARD_DIRECTORY = HOME_DIRECTORY + ADMIN_DASHBOARD_PAGE_NAME;
}
