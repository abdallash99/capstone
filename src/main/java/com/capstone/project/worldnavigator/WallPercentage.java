package com.capstone.project.worldnavigator;

public enum WallPercentage {
    DOOR_WITH_KEY(0,5),
    DOOR_WITHOUT_KEY(5,35),
    PAINTING(0,20),
    MIRROR(20,40),
    SELLER(40,43),
    CHEST(43,46),
    BLANK_WALL(46,100);

    public int getPercentage() {
        return min;
    }
    private final int min;
    private final int max;

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    WallPercentage(int min, int max) {
        this.min = min;
        this.max = max;
    }
}
