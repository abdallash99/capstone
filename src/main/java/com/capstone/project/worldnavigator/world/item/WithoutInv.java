package com.capstone.project.worldnavigator.world.item;

import java.util.Collection;
import java.util.Collections;

public class WithoutInv implements Inventory {
    private static final String ERROR_MASSAGE = "Can't do This Here";

    @Override
    public String toString() {
        return ERROR_MASSAGE;
    }

    @Override
    public String list() {
        return ERROR_MASSAGE;
    }

    @Override
    public String loot() {
        return ERROR_MASSAGE;
    }

    @Override
    public Collection<Portable> check() {
        return Collections.emptyList();
    }

    @Override
    public String buy(String item) {
        return ERROR_MASSAGE;
    }

    @Override
    public String sell(String item) {
        return ERROR_MASSAGE;
    }
}
