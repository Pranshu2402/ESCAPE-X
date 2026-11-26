
package com.escapex.services;

import com.escapex.database.DatabaseManager;
import com.escapex.models.GameProgress;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameProgressServiceTest {

    private static PlayerService playerService;
    private static GameProgressService progressService;

    @BeforeAll
    static void setup() {
        DatabaseManager.initializeDatabase();

        playerService = new PlayerService();
        progressService = new GameProgressService();
    }

    @Test
    void shouldSaveAndLoadGameProgress() throws SQLException {

        String username = "progress_" + UUID.randomUUID();

        int playerId = playerService.registerPlayer(username);

        GameProgress progress = new GameProgress(
            playerId,
            "Room 2",
            150,
            300,
            "[\"Key\", \"Map\"]"
        );

        progressService.saveProgress(progress);

        GameProgress loaded =
            progressService.loadProgress(playerId);

        assertNotNull(loaded);
        assertEquals(playerId, loaded.getPlayerId());
        assertEquals("Room 2", loaded.getCurrentRoom());
        assertEquals(150, loaded.getScore());
        assertEquals(300, loaded.getElapsedSeconds());
        assertEquals("[\"Key\", \"Map\"]", loaded.getInventoryJson());
    }

    @Test
    void shouldResumePlayerProgressAfterSaving()
            throws SQLException {

        // Simulate a registered player
        String username = "resume_" + UUID.randomUUID();

        int playerId = playerService.registerPlayer(username);

        // Simulate the player's progress before closing
        GameProgress beforeClosing = new GameProgress(
            playerId,
            "Room 2",
            250,
            420,
            "[\"Key\", \"Secret Map\"]"
        );

        // Save progress before application closes
        progressService.saveProgress(beforeClosing);

        // Simulate reopening the game and loading progress
        GameProgress afterReopening =
            progressService.loadProgress(playerId);

        // Verify that all progress was restored
        assertNotNull(afterReopening);

        assertEquals(playerId, afterReopening.getPlayerId());
        assertEquals("Room 2", afterReopening.getCurrentRoom());
        assertEquals(250, afterReopening.getScore());
        assertEquals(420, afterReopening.getElapsedSeconds());

        assertEquals(
            "[\"Key\", \"Secret Map\"]",
            afterReopening.getInventoryJson()
        );
    }

    @Test
    void shouldReturnNullWhenNoSavedProgressExists()
            throws SQLException {

        String username = "new_" + UUID.randomUUID();

        int playerId = playerService.registerPlayer(username);

        GameProgress progress =
            progressService.loadProgress(playerId);

        assertNull(progress);
    }
}
