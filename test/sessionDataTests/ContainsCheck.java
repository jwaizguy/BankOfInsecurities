package sessionDataTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dao.SessionDataDAO;
import com.dao.SessionDataDAOImpl;
import com.orm.SessionData;

public class ContainsCheck {

	private String name = "john";
	private String sessionID = "12345";
	
	@Test
	public void test() {
		SessionDataDAO sessionDataDAO = new SessionDataDAOImpl();
		
		SessionData sessionData1 = new SessionData(this.sessionID, this.name);
		
		SessionData sessionData2 = new SessionData(this.sessionID, this.name);
		
		sessionDataDAO.addSessionData(sessionData1);
		
		assertEquals(sessionDataDAO.isValidCredentials(sessionData2), true);
	}

}
