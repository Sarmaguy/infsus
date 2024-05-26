package com.example.demo.IntegrationTests;

import com.example.demo.Controllers.GameController;
import com.example.demo.models.Game;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class GameIntegrationTest {

    @Autowired
    private GameController gameController;

    @MockBean
    private Firestore firestore;

    @MockBean
    private CollectionReference collectionReference;

    @MockBean
    private ApiFuture<QuerySnapshot> querySnapshotApiFuture;

    private MockMvc mockMvc;

    @Test
    public void testGetAllGames() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        when(firestore.collection(anyString())).thenReturn(collectionReference);

        when(collectionReference.get()).thenReturn(querySnapshotApiFuture);

        QuerySnapshot querySnapshot = Mockito.mock(QuerySnapshot.class);
        when(querySnapshotApiFuture.get()).thenReturn(querySnapshot);

        when(querySnapshot.toObjects(Game.class)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/games"))
                .andExpect(status().isOk());
    }
}
