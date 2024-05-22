package com.example.demo.config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileInputStream;
import java.io.IOException;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public Firestore firebaseApp() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) { // Check if Firebase app with name "DEFAULT" doesn't exist
            FileInputStream serviceAccount = new FileInputStream("./appsettings.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        }

        return FirestoreClient.getFirestore(FirebaseApp.getInstance());
    }

}
