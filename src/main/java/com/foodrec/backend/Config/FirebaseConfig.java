//package com.foodrec.backend.Config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Configuration
//public class FirebaseConfig {
//    private static final Logger LOGGER = LoggerFactory.getLogger(FirebaseConfig.class);
//
//    @Bean
//    public FirebaseApp firebaseApp() throws IOException {
//
//        FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
//        FirebaseApp app = FirebaseApp.initializeApp(options);
//        LOGGER.info("FirebaseApp initialized successfully!");
//        return app;
//    }
//
//}
