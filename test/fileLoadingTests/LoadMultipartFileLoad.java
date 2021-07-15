package fileLoadingTests;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import com.command.fileManagement.LoadUploadedFileCommand;
import com.constants.ControllerConstants;

public class LoadMultipartFileLoad {

	private static Logger logger = Logger.getLogger(LoadMultipartFileLoad.class);
	
	@Test
	public void test() {
		LoadUploadedFileCommand loadUploadedFileCommand = new LoadUploadedFileCommand(ControllerConstants.DEFAULT_UPLOADED_FILENAME);
		loadUploadedFileCommand.execute();
		MultipartFile file = loadUploadedFileCommand.getOutputMultipartFile();
		
		
		assertEquals(file.getOriginalFilename(), ControllerConstants.DEFAULT_UPLOADED_FILENAME);
	}

}
