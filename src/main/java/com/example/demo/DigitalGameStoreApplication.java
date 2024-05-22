package com.example.demo;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@RestController

public class DigitalGameStoreApplication {

	private final Firestore db;

	@Autowired
	public DigitalGameStoreApplication(Firestore db) {
		this.db = db;
	}



	public static void main(String[] args) throws IOException {
        SpringApplication.run(DigitalGameStoreApplication.class, args);
	}


	@GetMapping("/hello")
	public String hello() {

		//get element from firebase with id "aljxa2Cfi6XBajSrWqqh" and put it in object of class Game
		DocumentReference docRef = db.collection("Game").document("aljxa2Cfi6XBajSrWqqh");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = null;
		try {
			document = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		if (document.exists()) {
			System.out.println("Document data: " + document.getData());
		} else {
			System.out.println("No such document!");
		}



		return "Hello World!";
	}
}
