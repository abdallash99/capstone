package com.capstone.project.worldnavigator.world;


import com.capstone.project.worldnavigator.world.portable.Light;
import com.capstone.project.worldnavigator.world.wall.Wall;

import java.util.*;

public class Room {
    private final List<Wall> walls;
    private final Light lights;

    public Room(List<Wall> walls, boolean isHasLight) {
        this.walls = walls;
        lights = new Light(isHasLight);
    }

    public Room() {
        this.walls = new ArrayList<>();
        this.lights = new Light();
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public Light getLight() {
        return lights;
    }
}
