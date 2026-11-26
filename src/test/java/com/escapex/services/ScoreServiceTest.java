
package com.escapex.services;

import com.escapex.database.DatabaseManager;
import com.escapex.models.GameProgress;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ScoreServiceTest {

    private ScoreService scoreService;

    private static PlayerService playerService;
    private static GameProgressService progressService;

    @BeforeAll
    static void initialize() {
        DatabaseManager.initializeDatabase();
        playerService = new PlayerService();
        progressService = new GameProgressService();
    }

    @BeforeEach
    void setup() {
        scoreService = new ScoreService();
    }

    @Test
    void shouldAwardPointsForSolvingPuzzle() {
        assertEquals(200,
            scoreService.awardPuzzlePoints(100));
    }

    @Test
    void shouldApplyHintPenalty() {
        assertEquals(80,
            scoreService.applyHintPenalty(100));
    }

    @Test
    void shouldApplyWrongAttemptPenalty() {
        assertEquals(90,
            scoreService.applyWrongAttemptPenalty(100));
    }

    @Test
    void shouldNotAllowScoreBelowZero() {
        assertEquals(0,
            scoreService.applyHintPenalty(10));
    }

    @Test
    void shouldAddBonusPoints() {
        assertEquals(150,
            scoreService.addBonusPoints(100, 50));
    }

    @Test
    void shouldRejectNegativeBonus() {
        assertThrows(
            IllegalArgumentException.class,
            () -> scoreService.addBonusPoints(100, -50)
        );
    }

    @Test
    void shouldSavePlayerScoreToDatabase()
            throws SQLException {

        String username = "score_" + UUID.randomUUID();

        int playerId =
            playerService.registerPlayer(username);

        int updatedScore =
            scoreService.updatePlayerScore(playerId, 100);

        assertEquals(100, updatedScore);

        GameProgress saved =
            progressService.loadProgress(playerId);

        assertNotNull(saved);
        assertEquals(100, saved.getScore());
    }

    @Test
    void shouldUpdateExistingPlayerScore()
            throws SQLException {

        String username = "update_" + UUID.randomUUID();

        int playerId =
            playerService.registerPlayer(username);

        scoreService.updatePlayerScore(playerId, 100);
        int updatedScore =
            scoreService.updatePlayerScore(playerId, -20);

        assertEquals(80, updatedScore);

        GameProgress saved =
            progressService.loadProgress(playerId);

        assertNotNull(saved);
        assertEquals(80, saved.getScore());
    }

    @Test
    void shouldRejectInvalidPlayerId() {

        assertThrows(
            IllegalArgumentException.class,
            () -> scoreService.updatePlayerScore(0, 100)
        );
    }
}
