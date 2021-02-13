package com.capstone.project.worldnavigator.world.portable;

import java.util.Collection;

public interface Inventory {
    String list();

    String loot();

    Collection<Portable> check();

    String buy(String type);

    String sell(String type);
}

