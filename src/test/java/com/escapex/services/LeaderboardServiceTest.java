
package com.escapex.services;

import com.escapex.database.DatabaseManager;
import com.escapex.models.GameProgress;
import com.escapex.models.LeaderboardEntry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardServiceTest {

    private static PlayerService playerService;
    private static GameProgressService progressService;
    private static LeaderboardService leaderboardService;

    @BeforeAll
    static void setup() {

        DatabaseManager.initializeDatabase();

        playerService = new PlayerService();
        progressService = new GameProgressService();
        leaderboardService = new LeaderboardService();
    }

    @Test
    void shouldRankPlayersByScoreAndCompletionTime()
            throws SQLException {

        String suffix = UUID.randomUUID().toString();

        int player1 =
            playerService.registerPlayer("first_" + suffix);

        int player2 =
            playerService.registerPlayer("second_" + suffix);

        int player3 =
            playerService.registerPlayer("third_" + suffix);

        int highScore = 500000000;

        progressService.saveProgress(
            new GameProgress(
                player1, "Completed", highScore, 300, "[]"
            )
        );

        progressService.saveProgress(
            new GameProgress(
                player2, "Completed", highScore + 100, 400, "[]"
            )
        );

        progressService.saveProgress(
            new GameProgress(
                player3, "Completed", highScore, 200, "[]"
            )
        );

        List<LeaderboardEntry> results =
            leaderboardService.getLeaderboard(Integer.MAX_VALUE);

        int firstPosition = -1;
        int secondPosition = -1;
        int thirdPosition = -1;

        for (int i = 0; i < results.size(); i++) {

            String username = results.get(i).getUsername();

            if (username.equals("first_" + suffix)) {
                firstPosition = i;
            }

            if (username.equals("second_" + suffix)) {
                secondPosition = i;
            }

            if (username.equals("third_" + suffix)) {
                thirdPosition = i;
            }
        }

        assertTrue(firstPosition >= 0);
        assertTrue(secondPosition >= 0);
        assertTrue(thirdPosition >= 0);

        assertTrue(secondPosition < thirdPosition);
        assertTrue(thirdPosition < firstPosition);
    }

    @Test
    void shouldRejectInvalidLeaderboardLimit() {

        assertThrows(
            IllegalArgumentException.class,
            () -> leaderboardService.getLeaderboard(0)
        );
    }
}
