
package com.escapex.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {

    @BeforeAll
    static void setup() {
        DatabaseManager.initializeDatabase();
    }

    @Test
    void shouldEnableForeignKeyEnforcement()
            throws SQLException {

        try (Connection connection =
                 DatabaseManager.getConnection()) {

            try (var statement = connection.createStatement();
                 var result = statement.executeQuery(
                     "PRAGMA foreign_keys"
                 )) {

                assertTrue(result.next());
                assertEquals(1, result.getInt(1));
            }
        }
    }

    @Test
    void shouldRejectProgressForNonexistentPlayer() {

        assertThrows(SQLException.class, () -> {

            try (Connection connection =
                     DatabaseManager.getConnection();
                 PreparedStatement statement =
                     connection.prepareStatement("""
                         INSERT INTO game_progress
                         (player_id, current_room, score)
                         VALUES (?, ?, ?)
                         """)) {

                statement.setInt(1, -999999);
                statement.setString(2, "Room 1");
                statement.setInt(3, 100);

                statement.executeUpdate();
            }
        });
    }
}
