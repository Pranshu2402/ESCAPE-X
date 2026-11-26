
package com.escapex.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Path;

public class DatabaseManager {

    private static final String DB_DIRECTORY =
            System.getProperty("user.home") + "/.escapex";

    private static final String DB_URL =
            "jdbc:sqlite:" + DB_DIRECTORY + "/escape_x.db";

    // Establish database connection
    public static Connection getConnection() throws SQLException {

        Connection connection =
            DriverManager.getConnection(DB_URL);

        // Enable foreign-key enforcement for this connection
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }

        return connection;
    }

    // Initialize database and create tables
    public static void initializeDatabase() {

        try {
            Files.createDirectories(Path.of(DB_DIRECTORY));

            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()) {

                // Players table
                statement.execute("""
                    CREATE TABLE IF NOT EXISTS players (
                        player_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT NOT NULL UNIQUE,
                        created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                    )
                """);

                // Game progress table
                statement.execute("""
                    CREATE TABLE IF NOT EXISTS game_progress (
                        progress_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        player_id INTEGER NOT NULL UNIQUE,
                        current_room TEXT,
                        score INTEGER DEFAULT 0,
                        elapsed_seconds INTEGER DEFAULT 0,
                        inventory_json TEXT DEFAULT '[]',
                        updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (player_id)
                            REFERENCES players(player_id)
                            ON DELETE CASCADE
                    )
                """);

                System.out.println(
                    "ESCAPE-X database initialized successfully!"
                );
            }

        } catch (Exception e) {
            System.err.println(
                "Database initialization failed: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }
}
