package com.capstone.project.util;

import java.util.Objects;

public class PlayerKey {
    private final String playerId;
    private final String worldId;

    public PlayerKey(String playerId, String worldId) {
        this.playerId = playerId;
        this.worldId = worldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerKey playerKey = (PlayerKey) o;

        if (!Objects.equals(playerId, playerKey.playerId)) return false;
        return Objects.equals(worldId, playerKey.worldId);
    }

    @Override
    public int hashCode() {
        int result = playerId != null ? playerId.hashCode() : 0;
        result = 31 * result + (worldId != null ? worldId.hashCode() : 0);
        return result;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getWorldId() {
        return worldId;
    }
}
