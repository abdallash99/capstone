package com.capstone.project.worldnavigator.world.item;

import java.io.Serializable;
import java.util.Objects;

public class Gold implements Portable, Serializable {
    private int amount;

    public Gold(int amount) {
        this.amount = amount;
    }

    public Gold() {
        this.amount = 0;
    }

    public void add(int amount) {
        this.amount += amount;
    }

    @Override
    public String toString() {
        return "game.world.portable.Gold" + " amount = " + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gold gold = (Gold) o;
        return amount == gold.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public int getPrice() {
        return amount;
    }
}
