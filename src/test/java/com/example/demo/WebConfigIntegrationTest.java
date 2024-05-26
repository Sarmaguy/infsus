package com.example.demo;

import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class WebConfigIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testFirestoreInitialization() {
        FirebaseApp firebaseApp = FirebaseApp.getInstance();
        assertNotNull(firebaseApp, "FirebaseApp should be initialized");

    }
}