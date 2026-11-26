
package com.escapex.services;

import com.escapex.models.GameProgress;

import java.sql.SQLException;

public class ScoreService {

    private static final int PUZZLE_REWARD = 100;
    private static final int HINT_PENALTY = 20;
    private static final int WRONG_ATTEMPT_PENALTY = 10;

    private final GameProgressService progressService;

    public ScoreService() {
        this.progressService = new GameProgressService();
    }

    // Award points for solving a puzzle
    public int awardPuzzlePoints(int currentScore) {
        return currentScore + PUZZLE_REWARD;
    }

    // Deduct points when a player uses a hint
    public int applyHintPenalty(int currentScore) {
        return Math.max(0, currentScore - HINT_PENALTY);
    }

    // Deduct points for an incorrect answer
    public int applyWrongAttemptPenalty(int currentScore) {
        return Math.max(0, currentScore - WRONG_ATTEMPT_PENALTY);
    }

    // Add bonus points
    public int addBonusPoints(int currentScore, int bonusPoints) {

        if (bonusPoints < 0) {
            throw new IllegalArgumentException(
                "Bonus points cannot be negative."
            );
        }

        return currentScore + bonusPoints;
    }

    // Update and save a player's score in SQLite
    public int updatePlayerScore(int playerId, int pointsChange)
            throws SQLException {

        if (playerId <= 0) {
            throw new IllegalArgumentException(
                "Invalid player ID."
            );
        }

        GameProgress progress =
            progressService.loadProgress(playerId);

        int currentScore = 0;

        if (progress != null) {
            currentScore = progress.getScore();
        }

        long updatedScore =
            (long) currentScore + pointsChange;

        int finalScore = (int) Math.max(
            0,
            Math.min(Integer.MAX_VALUE, updatedScore)
        );

        if (progress == null) {
            progress = new GameProgress(
                playerId,
                null,
                finalScore,
                0,
                "[]"
            );
        } else {
            progress.setScore(finalScore);
        }

        progressService.saveProgress(progress);

        return finalScore;
    }
}
