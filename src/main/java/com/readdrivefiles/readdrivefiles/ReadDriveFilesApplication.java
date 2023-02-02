package com.readdrivefiles.readdrivefiles;

import com.readdrivefiles.readdrivefiles.driver.ReadFolderListOfDriverApplication;
import com.readdrivefiles.readdrivefiles.readSpreadSheet.DriveListFileApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
public class ReadDriveFilesApplication {

	public static void main(String[] args) throws GeneralSecurityException, IOException {
		SpringApplication.run(ReadDriveFilesApplication.class, args);


//          DriveListFileApplication.getFileDetails();   //SpreadSheet Reader
		   ReadFolderListOfDriverApplication.readFolder(); //files inside folder id and name
	}

}
