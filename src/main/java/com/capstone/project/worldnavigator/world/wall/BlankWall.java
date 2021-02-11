package com.capstone.project.worldnavigator.world.wall;


import com.capstone.project.worldnavigator.world.portable.Inventory;
import com.capstone.project.worldnavigator.world.portable.Openable;

class BlankWall extends Wall {
    public BlankWall(Openable lock, Inventory inv) {
        super(lock, inv);
    }

    @Override
    public String look() {
        return "BlankWall";
    }
}
