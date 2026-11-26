
package com.escapex.models;

public class LeaderboardEntry {

    private String username;
    private int score;
    private int elapsedSeconds;

    public LeaderboardEntry(
            String username,
            int score,
            int elapsedSeconds) {

        this.username = username;
        this.score = score;
        this.elapsedSeconds = elapsedSeconds;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }
}
