package com.example.demo.Controllers;

import com.example.demo.models.DatabaseModel;
import com.example.demo.models.Game;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private Firestore firestore;

    @GetMapping
    public List<Game> getAllGames() {
        return DatabaseModel.getAll(firestore, Game.class);
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable String id) {
        return DatabaseModel.get(firestore, Game.class, id);
    }

    @PostMapping
    public ResponseEntity<String> createGame(@Valid @RequestBody Game game, BindingResult result) throws InterruptedException, ExecutionException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        if (isGameNameTaken(game.getGameName())) {
            return new ResponseEntity<>("Game name already exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(DatabaseModel.create(firestore, game).get().toString(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGame(@PathVariable String id, @Valid @RequestBody Game game, BindingResult result) throws InterruptedException, ExecutionException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        if (isGameNameTaken(game.getGameName(), id)) {
            return new ResponseEntity<>("Game name already exists", HttpStatus.BAD_REQUEST);
        }
        game.setId(id);
        DatabaseModel.update(firestore, game);
        return new ResponseEntity<>("Game updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable String id) {
        DatabaseModel.delete(firestore, id, Game.class);
        return new ResponseEntity<>("Game deleted successfully", HttpStatus.OK);
    }


    boolean isGameNameTaken(String gameName) throws ExecutionException, InterruptedException {
        QuerySnapshot snapshot = firestore.collection("Game")
                .whereEqualTo("gameName", gameName)
                .get()
                .get();
        return !snapshot.isEmpty();
    }

    boolean isGameNameTaken(String gameName, String id) throws ExecutionException, InterruptedException {
        QuerySnapshot snapshot = firestore.collection("Game")
                .whereEqualTo("gameName", gameName)
                .get()
                .get();
        return snapshot.getDocuments().stream().anyMatch(doc -> !doc.getId().equals(id));
    }
}
