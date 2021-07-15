package funTransferTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.command.FragmentDecimalCommand;

public class FragmentDecimalCommandTest {

	@Test
	public void test() {
		FragmentDecimalCommand fragmentDecimalCommand = new FragmentDecimalCommand(3.141568, 4);
		fragmentDecimalCommand.execute();
		
		double val1 = fragmentDecimalCommand.getNewValue();
		double val2 = fragmentDecimalCommand.getFunValue();
		
		assertEquals(val1, 3.140068, .000001);
		assertEquals(val2, .0015, .000001);
	}

}
