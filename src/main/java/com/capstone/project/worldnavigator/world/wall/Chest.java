package com.capstone.project.worldnavigator.world.wall;

import com.capstone.project.worldnavigator.world.item.Inventory;
import com.capstone.project.worldnavigator.world.item.Openable;
class Chest extends Wall {
    public Chest(Openable lock, Inventory inv) {
        super(lock, inv);
    }

    @Override
    public String look() {
        return "Chest";
    }

}
