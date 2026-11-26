
package com.escapex.services;

import com.escapex.database.DatabaseManager;
import com.escapex.models.GameProgress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameProgressService {

    // Save new progress or update existing progress
    public void saveProgress(GameProgress progress) throws SQLException {

        String sql = """
            INSERT INTO game_progress
            (player_id, current_room, score, elapsed_seconds,
             inventory_json, updated_at)
            VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
            ON CONFLICT(player_id) DO UPDATE SET
                current_room = excluded.current_room,
                score = excluded.score,
                elapsed_seconds = excluded.elapsed_seconds,
                inventory_json = excluded.inventory_json,
                updated_at = CURRENT_TIMESTAMP
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, progress.getPlayerId());
            statement.setString(2, progress.getCurrentRoom());
            statement.setInt(3, progress.getScore());
            statement.setInt(4, progress.getElapsedSeconds());
            statement.setString(5, progress.getInventoryJson());

            statement.executeUpdate();
        }
    }

    // Load saved progress for a player
    public GameProgress loadProgress(int playerId) throws SQLException {

        String sql = """
            SELECT * FROM game_progress
            WHERE player_id = ?
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, playerId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return new GameProgress(
                        result.getInt("player_id"),
                        result.getString("current_room"),
                        result.getInt("score"),
                        result.getInt("elapsed_seconds"),
                        result.getString("inventory_json")
                    );
                }
            }
        }

        return null;
    }
}
