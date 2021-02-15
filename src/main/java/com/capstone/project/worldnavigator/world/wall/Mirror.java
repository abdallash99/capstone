package com.capstone.project.worldnavigator.world.wall;

import com.capstone.project.worldnavigator.world.item.Inventory;
import com.capstone.project.worldnavigator.world.item.Openable;
class Mirror extends Wall {
    public Mirror(Openable lock, Inventory inv) {
        super(lock, inv);
    }

    @Override
    public String look() {
        return "You See a silhouette of you";
    }
}
