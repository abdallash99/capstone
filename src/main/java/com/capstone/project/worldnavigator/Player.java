package com.capstone.project.worldnavigator;

import static com.capstone.project.ProjectApplication.*;

import com.capstone.project.worldnavigator.world.item.WithInv;
import com.capstone.project.worldnavigator.world.item.Light;

import java.awt.*;
import java.io.Serializable;

public class Player implements Serializable {
    private final WithInv withInv;
    private int direction;
    private Point currentPosition;
    private final String worldId;

    public WithInv getWithInv() {
        return withInv;
    }

    public String getWorldId() {
        return worldId;
    }

    public Player(WithInv withInv, int direction, Point currentPosition, String worldId) {
        this.withInv = withInv;
        this.direction = direction;
        this.currentPosition = currentPosition;
        this.worldId = worldId;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Light getFlashLight() {
        return withInv.getLight();
    }

    public WithInv getInv() {
        return withInv;
    }

    public String left() {
        this.direction--;
        if (direction < 0) direction = 3;
        return "Turned Left";
    }

    public String right() {
        this.direction = (this.direction + 1) % ROOM_WALL_NUMBER;
        return "Turned Right";
    }

    @Override
    public String toString() {
        return "Player{" +
                "withInv=" + withInv +
                ", direction=" + direction +
                ", currentPosition=" + currentPosition +
                '}';
    }
    public String move(int direction) {
        currentPosition.x += MOVE_RATE[direction].getX();
        currentPosition.y += MOVE_RATE[direction].getY();
        return "Moved To The Next game.world.Room";
    }

    public String useFlashLight() {
        return withInv.getLight().use();
    }

    public String playerStatus() {
        return switch (direction) {
            case 0 -> "North";
            case 1 -> "East";
            case 2 -> "South";
            case 3 -> "West";
            default -> "Locking to moon";
        };
    }
}
