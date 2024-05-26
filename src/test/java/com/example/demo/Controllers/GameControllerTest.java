package com.example.demo.Controllers;

import com.example.demo.models.DatabaseModel;
import com.example.demo.models.Game;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(GameController.class)
@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Firestore firestore;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private GameController gameController;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    void getAllGames() throws Exception {
        Game game = new Game();
        game.setGameName("Test Game");

        try (MockedStatic<DatabaseModel> mockedDatabaseModel = mockStatic(DatabaseModel.class)) {
            mockedDatabaseModel.when(() -> DatabaseModel.getAll(firestore, Game.class))
                    .thenReturn(Collections.singletonList(game));

            mockMvc.perform(get("/api/games"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].gameName").value("Test Game"));

            mockedDatabaseModel.verify(() -> DatabaseModel.getAll(firestore, Game.class), times(1));
        }
    }


    @Test
    void getGameById() throws Exception {
        Game game = new Game();
        game.setGameName("Test Game");

        try (var mockedDatabaseModel = mockStatic(DatabaseModel.class)) {
            mockedDatabaseModel.when(() -> DatabaseModel.get(any(Firestore.class), any(Class.class), anyString()))
                    .thenReturn(game);

            mockMvc.perform(get("/api/games/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.gameName").value("Test Game"));

            mockedDatabaseModel.verify(() -> DatabaseModel.get(any(Firestore.class), any(Class.class), anyString()), times(1));
        }
    }


    @Test
    void createGame() throws Exception {
        Game game = new Game();
        game.setGameName("Test Game");

        given(bindingResult.hasErrors()).willReturn(false);

        try (MockedStatic<DatabaseModel> mockedDatabaseModel = mockStatic(DatabaseModel.class)) {
            mockedDatabaseModel.when(() -> DatabaseModel.create(firestore, game))
                    .thenReturn(mock(ApiFuture.class));

            mockMvc.perform(post("/api/games")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"gameName\":\"Test Game\"}"))
                    .andExpect(status().isCreated());

            mockedDatabaseModel.verify(() -> DatabaseModel.create(firestore, game), times(1));
        }
    }


    @Test
    void updateGame() throws Exception {
        Game game = new Game();
        game.setGameName("Test Game");

        given(bindingResult.hasErrors()).willReturn(false);

        try (var mockedDatabaseModel = mockStatic(DatabaseModel.class)) {
            mockedDatabaseModel.when(() -> DatabaseModel.update(any(Firestore.class), any(Game.class)))
                    .thenReturn(null);

            mockMvc.perform(put("/api/games/hiJG2VjIcS4pUF6Zc4mT")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"gameName\":\"Updated Game\"}"))
                    .andExpect(status().isOk());

            mockedDatabaseModel.verify(() -> DatabaseModel.update(any(Firestore.class), any(Game.class)), times(1));
        }
    }

    @Test
    void deleteGame() throws Exception {
        try (MockedStatic<DatabaseModel> mockedDatabaseModel = mockStatic(DatabaseModel.class)) {
            // Correct way to stub a static void method
            mockedDatabaseModel.when(() -> DatabaseModel.delete(any(Firestore.class), anyString(), any(Class.class)))
                    .thenAnswer(invocation -> null);

            mockMvc.perform(delete("/api/games/hiJG2VjIcS4pUF6Zc4mT"))
                    .andExpect(status().isOk());

            mockedDatabaseModel.verify(() -> DatabaseModel.delete(any(Firestore.class), anyString(), any(Class.class)), times(1));
        }
    }


}
