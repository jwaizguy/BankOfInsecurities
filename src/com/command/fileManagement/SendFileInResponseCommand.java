package com.command.fileManagement;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

import com.command.PermanentCommand;

public class SendFileInResponseCommand implements PermanentCommand {
	private static Logger logger = Logger.getLogger(SendFileInResponseCommand.class);
	private File file;
	private HttpServletResponse response;
	
	public SendFileInResponseCommand(File file, HttpServletResponse response) {
		this.file = file;
		this.response = response;
	}
	
	@Override
	public void execute() {
		if(! file.exists()) {
			String errorMessage = "File not found!";
			logger.info(errorMessage);
			OutputStream outputStream;
			try {
				outputStream = this.response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			} catch (IOException e) {
				logger.info(e.toString());
			}
			return;
		}
		
		String mimeType = URLConnection.guessContentTypeFromName(this.file.getName());
		if(mimeType == null) {
			logger.info("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}
		
		
		 
        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        try {
        	this.response.setContentType(mimeType);
    		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
    		response.setContentLength((int)file.length());
    		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			logger.info(e.toString());
		}
		
	}

}
