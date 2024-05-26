package com.example.demo.Controllers;

import com.example.demo.models.DatabaseModel;
import com.example.demo.models.Genre;
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

@WebMvcTest(GenreController.class)
@ExtendWith(MockitoExtension.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Firestore firestore;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private GenreController genreController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
    }

    @Test
    void getAllGenres() throws Exception {
        Genre genre = new Genre();
        genre.setGenreName("Test Genre");

        try (MockedStatic<DatabaseModel> mockedDatabaseModel = mockStatic(DatabaseModel.class)) {
            mockedDatabaseModel.when(() -> DatabaseModel.getAll(firestore, Genre.class))
                    .thenReturn(Collections.singletonList(genre));

            mockMvc.perform(get("/api/genres"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].genreName").value("Test Genre"));

            mockedDatabaseModel.verify(() -> DatabaseModel.getAll(firestore, Genre.class), times(1));
        }
    }

    @Test
    void getGenreById() throws Exception {
        Genre genre = new Genre();
        genre.setGenreName("Test Genre");

        try (var mockedDatabaseModel = mockStatic(DatabaseModel.class)) {
            mockedDatabaseModel.when(() -> DatabaseModel.get(any(Firestore.class), any(Class.class), anyString()))
                    .thenReturn(genre);

            mockMvc.perform(get("/api/genres/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.genreName").value("Test Genre"));

            mockedDatabaseModel.verify(() -> DatabaseModel.get(any(Firestore.class), any(Class.class), anyString()), times(1));
        }
    }


    @Test
    void deleteGenre() throws Exception {
        try (MockedStatic<DatabaseModel> mockedDatabaseModel = mockStatic(DatabaseModel.class)) {
            mockedDatabaseModel.when(() -> DatabaseModel.delete(any(Firestore.class), anyString(), any(Class.class)))
                    .thenAnswer(invocation -> null);

            mockMvc.perform(delete("/api/genres/hiJG2VjIcS4pUF6Zc4mT"))
                    .andExpect(status().isOk());

            mockedDatabaseModel.verify(() -> DatabaseModel.delete(any(Firestore.class), anyString(), any(Class.class)), times(1));
        }
    }
}
