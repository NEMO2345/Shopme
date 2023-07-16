package com.shopme.admin;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;





public class FileUploadUntil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUntil.class);

	public static void saveFile(String uploadDir,String fileName,MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
			
		}
		try(InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
			
		}catch(IOException ex) {
			throw new IOException("Could not save the file: " + fileName,ex);
		}
	}
	public static void  clearDir(String dir) {
		Path dirPath = Paths.get(dir);
		
		try {
			Files.list(dirPath).forEach(file ->{
				if(!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					}catch(IOException ex) {
						LOGGER.error("Could not delete file: "+ file);
						//System.out.println("Could not delete file: "+ file);
					}
				}
			});
		}catch(IOException ex) {
			//System.out.println("Could not list directory: " + dirPath);
			LOGGER.error("Could not list directory: " + dirPath);

		}
		
	}
}
