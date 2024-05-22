package com.example.demo.Controllers;

import com.example.demo.models.DatabaseModel;
import com.example.demo.models.Game;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private Firestore firestore;

    @GetMapping
    public List<Game> getAllGames(){
        return DatabaseModel.getAll(firestore, Game.class);
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable String id){
        return DatabaseModel.get(firestore, Game.class, id);
    }

    @PostMapping
    public String createGame(@RequestBody Game game) throws InterruptedException, ExecutionException {
        return DatabaseModel.create(firestore, game).get().toString();
    }

    @PutMapping("/{id}")
    public void updateGame(@PathVariable String id, @RequestBody Game game){
        game.setId(id);
        DatabaseModel.update(firestore, game);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable String id){
        DatabaseModel.delete(firestore, id, Game.class);
    }
}
