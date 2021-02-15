package com.capstone.project.worldnavigator;

import static com.capstone.project.ProjectApplication.*;

import com.capstone.project.worldnavigator.world.portable.WithInv;
import com.capstone.project.worldnavigator.world.portable.Key;
import com.capstone.project.worldnavigator.world.portable.Light;
import com.capstone.project.worldnavigator.world.wall.Wall;

import java.awt.*;

public class Player {
    private final WithInv withInv;
    private int direction;
    private Point currentPosition;
    private Wall frontWall;
    private boolean isAlive=true;

    public Wall getFrontWall() {
        return frontWall;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Player(WithInv withInv) {
        this.withInv = withInv;
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
                ", frontWall=" + frontWall +
                ", isAlive=" + isAlive +
                '}';
    }

    protected String move(int direction) {
        currentPosition.x += MOVE_RATE[direction].getX();
        currentPosition.y += MOVE_RATE[direction].getY();
        return "Moved To The Next game.world.Room";
    }

    public void setFrontWall(Wall wall) {
        this.frontWall = wall;
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

    public String look() {
        return frontWall.look();
    }

    public String check() {
        if (frontWall.getLock().isLocked()) {
            return frontWall.getLock().check();
        } else if (!frontWall.getInv().check().isEmpty()) {
            getInv().addAll(frontWall.getInv().check());
            return frontWall.getInv().loot();
        }
        return "No Item Here";
    }

    public String checkKey() {
        return frontWall.getLock().check();
    }

    public String open() {
        return frontWall.getLock().open();
    }

    public String useFlashLight() {
        return getFlashLight().use();
    }

    public String useKey(String type) {
        return frontWall.unlock(new Key(type));
    }

    public String list() {
        return frontWall.getInv().list();
    }

    public String buy(String type) {
        return frontWall.getInv().buy(type);
    }

    public String sell(String type) {
        return frontWall.getInv().sell(type);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void dead() {
        isAlive=false;
    }
}
