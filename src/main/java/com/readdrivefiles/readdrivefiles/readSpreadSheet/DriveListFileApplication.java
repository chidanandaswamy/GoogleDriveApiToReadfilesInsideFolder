package com.readdrivefiles.readdrivefiles.readSpreadSheet;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.readdrivefiles.readdrivefiles.DTO.Drive;
import com.readdrivefiles.readdrivefiles.Repo.DriverRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class DriveListFileApplication {

    private static DriverRepo repo;
    @Autowired
    public DriveListFileApplication(DriverRepo repo) {
        this.repo = repo;
    }

//    @Autowired
//   private  DriverRepo repo ;

    private static final Logger LOGGER = LoggerFactory.getLogger(DriveListFileApplication.class);

        private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
        private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        private static final String TOKENS_DIRECTORY_PATH = "tokens";

        /**
         * Global instance of the scopes required by this quickstart.
         * If modifying these scopes, delete your previously saved tokens/ folder.
         */
        private static final List<String> SCOPES = List.of(DriveScopes.DRIVE_METADATA_READONLY, SheetsScopes.SPREADSHEETS_READONLY);
//            Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
        private static final String CREDENTIALS_FILE_PATH = "/Drivefile.json";

        /**
         * Creates an authorized Credential object.
         *
         * @param HTTP_TRANSPORT The network HTTP Transport.
         * @return An authorized Credential object.
         * @throws IOException If the credentials.json file cannot be found.
         */
        private static Credential getCredentials ( final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
            // Load client secrets.
            GoogleAuthorizationCodeFlow flow = null;
            try {
                InputStream in = DriveListFileApplication.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
                if (in == null) {
                    throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
                }
                GoogleClientSecrets clientSecrets =
                        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

                // Build flow and trigger user authorization request.
                flow = new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                        .setAccessType("offline")
                        .build();
                LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8081).build();
                return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

            } catch (Exception e) {
                e.printStackTrace();
            }
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8081).build();
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        }

        /**
         * Prints the names and majors of students in a sample spreadsheet:
         * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
         */


        public static void getFileDetails ( final String spreadsheetId) //String... args)
            throws IOException, GeneralSecurityException {
        //SpringApplication.run(DriveListFileApplication.class, args);
           try{
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        final String spreadsheetId = "1-f9IW8acyP7E1IbAsyMVksBFwsHM29wiWBEYfKtsitY";

        System.out.println(spreadsheetId);

        final String range = "A2:F1000";
        Sheets service =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");


            int count = 0;
            List<Drive> drive1 = new ArrayList<>();

            for (List row : values) {
//                 Print columns A and E, which correspond to indices 0 and 4.
//                System.out.printf("%s, %s\n", row.get(0), row.get(3), row.get(4));

                System.out.println(row.get(0));//f
                System.out.println(row.get(1));//l
                System.out.println(row.get(2));//email
                System.out.println(row.get(3));//dur
                System.out.println(row.get(4));//join
                System.out.println(row.get(5));//exit

                Drive drive = new Drive();

                drive.setFirstName(String.valueOf(row.get(0)));
                drive.setLastName(String.valueOf(row.get(1)));
                drive.setEmail(String.valueOf(row.get(2)));
                drive.setDuration(String.valueOf(row.get(3)));
                drive.setTime_joined(String.valueOf(row.get(4)));
                drive.setTime_exited(String.valueOf(row.get(5)));



                drive1.add(drive);

                repo.save(drive);


            }

        }

           }catch (Exception e){
               e.printStackTrace();
           }
    }
    }
