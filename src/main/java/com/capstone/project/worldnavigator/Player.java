package com.capstone.project.worldnavigator;


import com.capstone.project.worldnavigator.world.portable.Inv;
import com.capstone.project.worldnavigator.world.portable.Key;
import com.capstone.project.worldnavigator.world.portable.Light;
import com.capstone.project.worldnavigator.world.wall.Wall;

import java.awt.*;

public class Player {
    private final Inv inv;
    private int direction;
    private Point currentPosition;
    private Wall frontWall;
    private boolean isAlive=true;

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Player() {
        this.inv = new Inv();
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
        return inv.getLight();
    }

    public Inv getInv() {
        return inv;
    }

    public String left() {
        this.direction--;
        if (direction < 0) direction = 3;
        return "Turned Left";
    }

    public String right() {
        this.direction = (this.direction + 1) % WorldNavigator.ROOM_WALL;
        return "Turned Left";
    }

    protected String move(int direction) {
        currentPosition.x += WorldNavigator.MOVE_RATE[direction][0];
        currentPosition.y += WorldNavigator.MOVE_RATE[direction][1];
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
}
