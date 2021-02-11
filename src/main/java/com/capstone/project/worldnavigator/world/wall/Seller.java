package com.capstone.project.worldnavigator.world.wall;

import com.capstone.project.worldnavigator.world.portable.Inventory;
import com.capstone.project.worldnavigator.world.portable.Openable;
class Seller extends Wall {

    public Seller(Openable lock, Inventory inv) {
        super(lock, inv);
    }

    @Override
    public String look() {
        return "Seller";
    }
}
