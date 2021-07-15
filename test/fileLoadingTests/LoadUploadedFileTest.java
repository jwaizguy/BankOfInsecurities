package fileLoadingTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.command.fileManagement.LoadUploadedFileCommand;
import com.constants.ControllerConstants;

public class LoadUploadedFileTest {

	@Test
	public void test() {
		LoadUploadedFileCommand fileLoader = new LoadUploadedFileCommand(ControllerConstants.DEFAULT_UPLOADED_FILENAME);
		fileLoader.execute();
		
		assertEquals(fileLoader.getOutputFile().exists(), true);
	}

}
