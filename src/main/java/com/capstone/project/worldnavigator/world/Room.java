package com.capstone.project.worldnavigator.world;


import com.capstone.project.worldnavigator.world.portable.WithInv;
import com.capstone.project.worldnavigator.world.portable.Light;
import com.capstone.project.worldnavigator.world.wall.Wall;

import java.util.*;

public class Room {
    private final List<Wall> walls;
    private final Light lights;
    private final WithInv withInv;

    public Room(List<Wall> walls, Light lights, WithInv withInv) {
        this.walls = walls;
        this.lights = lights;
        this.withInv = withInv;
    }

    public WithInv getInv() {
        return withInv;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public Light getLight() {
        return lights;
    }
}
