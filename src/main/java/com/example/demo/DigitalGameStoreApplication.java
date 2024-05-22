package com.example.demo;

import com.example.demo.models.DatabaseModel;
import com.example.demo.models.Game;
import com.example.demo.models.Genre;
import com.example.demo.models.Platform;
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
import java.util.List;
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
	public String hello() throws ExecutionException, InterruptedException {

		DatabaseModel.get(db, Game.class, "aljxa2Cfi6XBajSrWqqh");

		Game game2 = new Game();

		game2.setGameName("New Game");
		game2.setDescription("New Description");
		game2.setGamePrice(50.0);
		game2.setAverageScore(5.0);

		DatabaseModel.create(db, game2);


		List<Game> games = DatabaseModel.getAll(db, Game.class);

		for (Game game : games) {
			System.out.println(game.getGameName());
		}

		game2.setGamePrice(100.0);
		DatabaseModel.update(db, game2);

		DatabaseModel.delete(db, game2.getId(), Game.class);

		Platform platform = new Platform();
		platform.setPlatformName("New Platform");

		DatabaseModel.create(db, platform);

		Genre genre = new Genre();
		genre.setGenreName("New Genre");

		DatabaseModel.create(db, genre);


		return "Hello World!";
	}
}
