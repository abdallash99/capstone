package com.capstone.project.worldnavigator.world;


import com.capstone.project.worldnavigator.WorldNavigator;
import com.capstone.project.worldnavigator.world.portable.Light;
import com.capstone.project.worldnavigator.world.portable.WithoutInv;
import com.capstone.project.worldnavigator.world.portable.WithoutLock;
import com.capstone.project.worldnavigator.world.wall.NullWall;
import com.capstone.project.worldnavigator.world.wall.Wall;

import java.awt.*;
import java.util.List;

public class Map {
    private final List<List<Room>> rooms;

    public Map(List<List<Room>> rooms) {
        this.rooms = rooms;
    }

    public Wall getWall(Point currentPosition, int direction) {
        int x = currentPosition.x;
        int y = currentPosition.y;
        if (x < rooms.size() && y < rooms.get(x).size() && direction < WorldNavigator.ROOM_WALL) {
            return rooms.get(x).get(y).getWalls().get(direction);
        } else return new NullWall(new WithoutLock(true), new WithoutInv());
    }

    public Room getRoom(Point currentPosition) {
        return rooms.get(currentPosition.x).get(currentPosition.y);
    }

    public Light getLight(Point currentPosition) {
        int x = currentPosition.x;
        int y = currentPosition.y;
        if (x < rooms.size() && y < rooms.get(x).size()) {
            return rooms.get(x).get(y).getLight();
        }
        return new Light();
    }
}
