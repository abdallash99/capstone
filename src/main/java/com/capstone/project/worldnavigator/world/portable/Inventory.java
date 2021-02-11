package com.capstone.project.worldnavigator.world.portable;

import java.util.List;

public interface Inventory {
    String list();

    String loot();

    List<Portable> check();

    String buy(String type);

    String sell(String type);
}

