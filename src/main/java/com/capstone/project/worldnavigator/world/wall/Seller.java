package com.capstone.project.worldnavigator.world.wall;

import com.capstone.project.worldnavigator.world.item.Inventory;
import com.capstone.project.worldnavigator.world.item.Openable;
class Seller extends Wall {

    public Seller(Openable lock, Inventory inv) {
        super(lock, inv);
    }

    @Override
    public String look() {
        return "Seller";
    }
}
