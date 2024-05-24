package com.example.demo.Controllers;

import com.example.demo.models.DatabaseModel;
import com.example.demo.models.Genre;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private Firestore firestore;

    @GetMapping
    public List<Genre> getAllGenres() {
        return DatabaseModel.getAll(firestore, Genre.class);
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable String id) {
        return DatabaseModel.get(firestore, Genre.class, id);
    }

    @PostMapping
    public ResponseEntity<String> createGenre(@Valid @RequestBody Genre genre, BindingResult result) throws ExecutionException, InterruptedException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        if (isGenreNameTaken(genre.getGenreName())) {
            return new ResponseEntity<>("Genre name already exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(DatabaseModel.create(firestore, genre).get().toString(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGenre(@PathVariable String id, @Valid @RequestBody Genre genre, BindingResult result) throws InterruptedException, ExecutionException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        if (isGenreNameTaken(genre.getGenreName(), id)) {
            return new ResponseEntity<>("Genre name already exists", HttpStatus.BAD_REQUEST);
        }
        genre.setId(id);
        DatabaseModel.update(firestore, genre);
        return new ResponseEntity<>("Genre updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable String id) {
        DatabaseModel.delete(firestore, id, Genre.class);
        return new ResponseEntity<>("Genre deleted successfully", HttpStatus.OK);
    }

    private boolean isGenreNameTaken(String genreName) throws ExecutionException, InterruptedException {
        QuerySnapshot snapshot = firestore.collection("Genre")
                .whereEqualTo("genreName", genreName)
                .get()
                .get();
        return !snapshot.isEmpty();
    }

    private boolean isGenreNameTaken(String genreName, String id) throws ExecutionException, InterruptedException {
        QuerySnapshot snapshot = firestore.collection("Genre")
                .whereEqualTo("genreName", genreName)
                .get()
                .get();
        return snapshot.getDocuments().stream().anyMatch(doc -> !doc.getId().equals(id));
    }
}
