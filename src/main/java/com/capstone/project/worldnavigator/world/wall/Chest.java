package com.capstone.project.worldnavigator.world.wall;

import com.capstone.project.worldnavigator.world.portable.Inventory;
import com.capstone.project.worldnavigator.world.portable.Openable;
class Chest extends Wall {
    public Chest(Openable lock, Inventory inv) {
        super(lock, inv);
    }

    @Override
    public String look() {
        return "Chest";
    }

}
