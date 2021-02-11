package com.capstone.project.worldnavigator;

public enum GameStatus {
    START(true),
    NOT_STARTED(false),
    NOT_JOINED(false);

    private final boolean wait;
    GameStatus(boolean wait) {
        this.wait=wait;
    }

    public boolean getWait() {
        return wait;
    }
}
