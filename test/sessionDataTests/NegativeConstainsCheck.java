package sessionDataTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dao.SessionDataDAO;
import com.dao.SessionDataDAOImpl;
import com.orm.SessionData;

public class NegativeConstainsCheck {

	private String name = "john";
	private String name2 = "jane";
	private String sessionID = "12345";
	private String sessionID2 = "234";
	
	@Test
	public void test() {
		SessionDataDAO sessionDataDAO = new SessionDataDAOImpl();
		
		SessionData sessionData1 = new SessionData(this.sessionID, this.name);
		
		SessionData sessionData2 = new SessionData(this.sessionID, this.name2);
		
		SessionData sessionData3 = new SessionData(this.sessionID2, this.name);
		
		sessionDataDAO.addSessionData(sessionData1);
		
		assertEquals(sessionDataDAO.isValidCredentials(sessionData2), false);
		assertEquals(sessionDataDAO.isValidCredentials(sessionData3), false);
	}

}
