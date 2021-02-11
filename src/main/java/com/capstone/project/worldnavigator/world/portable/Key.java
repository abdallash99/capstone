package com.capstone.project.worldnavigator.world.portable;

import java.util.Objects;

public class Key implements Portable {
    private final String keyType;
    private final Gold price;

    public Key(String keyType, Gold price) {
        this.keyType = keyType;
        this.price = price;
    }

    public Key(String keyType) {
        this.keyType = keyType;
        this.price = new Gold(0);
    }

    public Key() {
        keyType = "none";
        this.price = new Gold(0);
    }

    public String getKeyType() {
        return keyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Objects.equals(keyType, key.keyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyType);
    }

    @Override
    public String toString() {
        return keyType + " key, price " + getPrice();
    }

    @Override
    public int getPrice() {
        return price.getPrice();
    }
}
