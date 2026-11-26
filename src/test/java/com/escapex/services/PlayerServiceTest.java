
package com.escapex.services;

import com.escapex.database.DatabaseManager;
import com.escapex.models.PlayerProfile;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceTest {

    private static PlayerService playerService;

    @BeforeAll
    static void setup() {
        DatabaseManager.initializeDatabase();
        playerService = new PlayerService();
    }

    @Test
    void shouldRegisterNewPlayer() throws SQLException {

        String username = "test_" + UUID.randomUUID();

        int playerId = playerService.registerPlayer(username);

        assertTrue(playerId > 0);

        assertEquals(
            playerId,
            playerService.getPlayerId(username)
        );
    }

    @Test
    void shouldRejectDuplicateUsername() throws SQLException {

        String username = "duplicate_" + UUID.randomUUID();

        playerService.registerPlayer(username);

        assertThrows(
            IllegalArgumentException.class,
            () -> playerService.registerPlayer(username)
        );
    }

    @Test
    void shouldRejectEmptyUsername() {

        assertThrows(
            IllegalArgumentException.class,
            () -> playerService.registerPlayer(" ")
        );
    }

    @Test
    void shouldRetrievePlayerProfile() throws SQLException {

        String username = "profile_" + UUID.randomUUID();

        int playerId = playerService.registerPlayer(username);

        PlayerProfile profile =
            playerService.getPlayerProfile(username);

        assertNotNull(profile);
        assertEquals(playerId, profile.getPlayerId());
        assertEquals(username, profile.getUsername());
        assertNotNull(profile.getCreatedAt());
    }

    @Test
    void shouldReturnNullForUnknownProfile() throws SQLException {

        PlayerProfile profile =
            playerService.getPlayerProfile(
                "unknown_" + UUID.randomUUID()
            );

        assertNull(profile);
    }
}
