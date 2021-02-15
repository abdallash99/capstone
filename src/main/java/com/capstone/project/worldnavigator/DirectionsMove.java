package com.capstone.project.worldnavigator;

public enum DirectionsMove {
    UP(-1,0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    DirectionsMove(int x, int y) {
        this.x=x;
        this.y=y;
    }

}
