package com.capstone.project.worldnavigator.world.wall;

import com.capstone.project.worldnavigator.world.item.Inventory;
import com.capstone.project.worldnavigator.world.item.Openable;
public class WallFactory {
    private WallFactory() {
    }

    public static Wall getWall(String wallType, Inventory inv, Openable lock) {
        if (wallType.equalsIgnoreCase("Painting")) {
            return new Painting(lock, inv);
        } else if (wallType.equalsIgnoreCase("Door")) {
            return new Door(lock, inv);
        } else if (wallType.equalsIgnoreCase("Mirror")) {
            return new Mirror(lock, inv);
        } else if (wallType.equalsIgnoreCase("Seller")) {
            return new Seller(lock, inv);
        } else if (wallType.equalsIgnoreCase("Chest")) {
            return new Chest(lock, inv);
        } else if (wallType.equalsIgnoreCase("Wall")) {
            return new BlankWall(lock, inv);
        } else {
            return new NullWall(lock, inv);
        }
    }
}
