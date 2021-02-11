package com.capstone.project.worldnavigator.world.wall;

import com.capstone.project.worldnavigator.world.portable.Inventory;
import com.capstone.project.worldnavigator.world.portable.Openable;
class Mirror extends Wall {
    public Mirror(Openable lock, Inventory inv) {
        super(lock, inv);
    }

    @Override
    public String look() {
        return "You See a silhouette of you";
    }
}
