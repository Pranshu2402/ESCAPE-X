
package com.escapex.models;

public class PlayerProfile {

    private int playerId;
    private String username;
    private String createdAt;

    public PlayerProfile(
            int playerId,
            String username,
            String createdAt) {

        this.playerId = playerId;
        this.username = username;
        this.createdAt = createdAt;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
