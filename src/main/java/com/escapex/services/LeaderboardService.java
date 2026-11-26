
package com.escapex.services;

import com.escapex.database.DatabaseManager;
import com.escapex.models.LeaderboardEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardService {

    // Retrieve the top players
    public List<LeaderboardEntry> getLeaderboard(int limit)
            throws SQLException {

        if (limit <= 0) {
            throw new IllegalArgumentException(
                "Leaderboard limit must be greater than zero."
            );
        }

        String sql = """
            SELECT
                players.username,
                game_progress.score,
                game_progress.elapsed_seconds
            FROM game_progress
            INNER JOIN players
                ON players.player_id = game_progress.player_id
            ORDER BY
                game_progress.score DESC,
                game_progress.elapsed_seconds ASC
            LIMIT ?
            """;

        List<LeaderboardEntry> leaderboard = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql)) {

            statement.setInt(1, limit);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    LeaderboardEntry entry =
                        new LeaderboardEntry(
                            result.getString("username"),
                            result.getInt("score"),
                            result.getInt("elapsed_seconds")
                        );

                    leaderboard.add(entry);
                }
            }
        }

        return leaderboard;
    }
}
