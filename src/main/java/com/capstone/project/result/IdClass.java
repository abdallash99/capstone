package com.capstone.project.result;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class IdClass implements Serializable {
    private String worldId;
    private String username;

    public IdClass(String worldId, String username) {
        this.worldId = worldId;
        this.username = username;
    }

    @Override
    public String toString() {
        return "IdClass{" +
                "worldId='" + worldId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public IdClass() {
    }

    public String getWorldId() {
        return worldId;
    }

    public void setWorldId(String worldId) {
        this.worldId = worldId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
