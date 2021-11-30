package timeUtilityTests;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import com.utility.TimeUtilityFunctions;

public class MonthsBeforeTodayTest {
	private final int baseYear = 2016;
	private final int baseMonth = 5;
	private final int defaultValue = 0;
	
	/* Making sure that year of months before date function is returning proper values */
	@Test
	public void test() {
		int output;
		TimeUtilityFunctions timeUtility = new TimeUtilityFunctions();
		Calendar date1 = Calendar.getInstance();
		date1.set(this.baseYear, this.baseMonth + 1, this.defaultValue);
		
		// set back 5 months, because it's month 5, it should be 0 years ago
		output = timeUtility.getYearOfNMonthsBeforeDate(date1, this.baseMonth);
		assertEquals(output, this.baseYear - 0);
		
		// set back n + 1 months, it should be 1 year ago
		output = timeUtility.getYearOfNMonthsBeforeDate(date1, this.baseMonth + 1);
		assertEquals(output, this.baseYear - 1);
		
		// set back 23 months, should say it is 2 years ago
		output = timeUtility.getYearOfNMonthsBeforeDate(date1, 23);
		assertEquals(output, this.baseYear - 2);
		
	}

}
