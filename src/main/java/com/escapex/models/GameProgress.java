
package com.escapex.models;

public class GameProgress {

    private int playerId;
    private String currentRoom;
    private int score;
    private int elapsedSeconds;
    private String inventoryJson;

    public GameProgress(
            int playerId,
            String currentRoom,
            int score,
            int elapsedSeconds,
            String inventoryJson) {

        this.playerId = playerId;
        this.currentRoom = currentRoom;
        this.score = score;
        this.elapsedSeconds = elapsedSeconds;
        this.inventoryJson = inventoryJson;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public int getScore() {
        return score;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    public String getInventoryJson() {
        return inventoryJson;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setElapsedSeconds(int elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }

    public void setInventoryJson(String inventoryJson) {
        this.inventoryJson = inventoryJson;
    }
}
