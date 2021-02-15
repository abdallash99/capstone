package com.capstone.project.worldnavigator.world.wall;

import com.capstone.project.worldnavigator.world.item.Inventory;
import com.capstone.project.worldnavigator.world.item.Openable;
public class NullWall extends Wall {
    public NullWall(Openable lock, Inventory inv) {
        super(lock, inv);
    }

    @Override
    public String look() {
        return "Null Wall";
    }
}
