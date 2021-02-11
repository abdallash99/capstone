package com.capstone.project.worldnavigator.world.wall;

import com.capstone.project.worldnavigator.world.portable.Inventory;
import com.capstone.project.worldnavigator.world.portable.Openable;
public class Door extends Wall {
    public Door(Openable lock, Inventory inv) {
        super(lock, inv);
    }

    @Override
    public String look() {
        return "Door";
    }
}
