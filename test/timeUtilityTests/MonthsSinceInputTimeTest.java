package timeUtilityTests;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import com.utility.TimeUtilityFunctions;

public class MonthsSinceInputTimeTest {
	private final int baseYear = 2015;
	private final int baseMonth = 4;

	private final int yearOffset = 3;
	private final int monthOffset = 3;
	private final int defaultValue = 0;
	
	@Test
	public void test() {
		int output;
		TimeUtilityFunctions timeUtility = new TimeUtilityFunctions();
		
		Calendar date1 = Calendar.getInstance();
		date1.set(this.baseYear, this.baseMonth, this.defaultValue);
		
		Calendar date2 = Calendar.getInstance();
		date2.set(this.baseYear + this.yearOffset, this.baseMonth + this.monthOffset, this.defaultValue);
		
		output = timeUtility.monthsSinceInputTime(date1, date2);
		int realOutput = this.yearOffset * 12 + this.monthOffset;
		assertEquals(output, realOutput);
	}

}
