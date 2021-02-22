package com.capstone.project.util;

import java.io.Serializable;
import java.util.Objects;

public class RoomKey implements Serializable {
    private final String worldId;
    private final int x;
    private final int y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomKey roomKey = (RoomKey) o;

        if (x != roomKey.x) return false;
        if (y != roomKey.y) return false;
        return Objects.equals(worldId, roomKey.worldId);
    }

    @Override
    public int hashCode() {
        int result = worldId != null ? worldId.hashCode() : 0;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    public RoomKey(String worldId, int x, int y) {
        this.worldId = worldId;
        this.x = x;
        this.y = y;
    }

    public String getWorldId() {
        return worldId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
