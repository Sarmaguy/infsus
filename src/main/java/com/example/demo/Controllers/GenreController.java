package com.example.demo.Controllers;

import com.example.demo.models.DatabaseModel;
import com.example.demo.models.Genre;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String createGenre(@RequestBody Genre genre) throws ExecutionException, InterruptedException {
        return DatabaseModel.create(firestore, genre).get().toString();
    }

    @PutMapping("/{id}")
    public void updateGenre(@PathVariable String id, @RequestBody Genre genre) {
        genre.setId(id);
        DatabaseModel.update(firestore, genre);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable String id) {
        DatabaseModel.delete(firestore, id, Genre.class);
    }
}