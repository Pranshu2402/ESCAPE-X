
package com.escapex.services;

import com.escapex.database.DatabaseManager;
import com.escapex.models.PlayerProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerService {

    // Register a new player
    public int registerPlayer(String username) throws SQLException {

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Username cannot be empty."
            );
        }

        username = username.trim();

        try (Connection connection = DatabaseManager.getConnection()) {

            String checkSql =
                "SELECT player_id FROM players WHERE username = ?";

            try (PreparedStatement checkStatement =
                     connection.prepareStatement(checkSql)) {

                checkStatement.setString(1, username);

                try (ResultSet result = checkStatement.executeQuery()) {

                    if (result.next()) {
                        throw new IllegalArgumentException(
                            "Username already exists!"
                        );
                    }
                }
            }

            String insertSql =
                "INSERT INTO players (username) VALUES (?)";

            try (PreparedStatement insertStatement =
                     connection.prepareStatement(
                         insertSql,
                         Statement.RETURN_GENERATED_KEYS
                     )) {

                insertStatement.setString(1, username);
                insertStatement.executeUpdate();

                try (ResultSet keys =
                         insertStatement.getGeneratedKeys()) {

                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        }

        throw new SQLException("Player registration failed.");
    }

    // Find player ID using username
    public int getPlayerId(String username) throws SQLException {

        String sql =
            "SELECT player_id FROM players WHERE username = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return result.getInt("player_id");
                }
            }
        }

        return -1;
    }

    // Retrieve complete player profile
    public PlayerProfile getPlayerProfile(String username)
            throws SQLException {

        String sql = """
            SELECT player_id, username, created_at
            FROM players
            WHERE username = ?
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    return new PlayerProfile(
                        result.getInt("player_id"),
                        result.getString("username"),
                        result.getString("created_at")
                    );
                }
            }
        }

        return null;
    }
}
