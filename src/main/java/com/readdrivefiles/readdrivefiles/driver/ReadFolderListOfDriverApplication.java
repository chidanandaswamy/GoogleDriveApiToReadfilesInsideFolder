package com.readdrivefiles.readdrivefiles.driver;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;


import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.readdrivefiles.readdrivefiles.readSpreadSheet.DriveListFileApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;


//@SpringBootApplication
public class ReadFolderListOfDriverApplication {

//	//public static void main(String[] args) {
//		SpringApplication.run(ReadFolderListOfDriverApplication.class, args);
//	}

    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String TOKENS_DIRECTORY_PATH = "AIzaSyCBJJ_8uaA4MQxTtkmHD5SfX0EyprQepIk";

    private static final List<String> SCOPES =
//            Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY, SheetsScopes.SPREADSHEETS_READONLY);
            List.of(DriveScopes.DRIVE_METADATA_READONLY, SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/Drivefile.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        Credential credential = null;
        try {
            InputStream in = ReadFolderListOfDriverApplication.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }
            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8081).build();
            credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
            //returns an authorized Credential object.
            return credential;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return credential;
    }

    public static void readFolder() //String... args)

            throws IOException, GeneralSecurityException {

      //  SpringApplication.run(ReadFolderListOfDriverApplication.class, args);

                try {



        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the names and IDs for up to 10 files.
//        Scanner sc = new Scanner(System.in);
        String str = "'" + "12B8TMt5eTBOstk8fQ4zk3SFd2lHR-wak" + "'" + " in parents";
        FileList result = service.files().list()//.setQ("modifiedTime > '2023-01-31' and fullText contains 'Meeting Reports'")
                .setQ(str)
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();

        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {

                System.out.printf("%s (%s)\n", file.getName(), file.getId());

                DriveListFileApplication.getFileDetails(file.getId());   // calling ReadDriveFileApplication to read the files of passed id frome here
            }
        }

//        System.out.println("Enter date : ");
//        String date = sc.next();
//        String dateFolder = "";
        for (File file : files) {

            String str1 = "'" + file.getId() + "'" + " in parents";
            FileList result1 = service.files().list()
                    .setQ(str1)
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();

            List<File> files1 = result1.getFiles();
            if (files == null || files.isEmpty()) {
                System.out.println("No files found.");
            } else {
//                System.out.println("Files:");
                for (File file1 : files1) {

                    System.out.printf("%s (%s)\n", file1.getName(), file1.getId());

                }
            }
        }
                }catch (Exception e){
                    e.printStackTrace();
                }
    }


}