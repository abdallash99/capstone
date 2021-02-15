package com.capstone.project.worldnavigator.world.portable;

import java.util.Objects;

public class Light implements Portable {
    private final Gold price;
    private boolean isIlluminate;
    private final boolean isHaseLight;

    public Light(Gold price, boolean isIlluminate, boolean isHaseLight) {
        this.price = price;
        this.isIlluminate = isIlluminate;
        this.isHaseLight = isHaseLight;
    }

    public Light(boolean isHaseLight) {
        this.isHaseLight = isHaseLight;
        this.price = new Gold(0);
    }

    public Light() {
        isHaseLight = false;
        this.price = new Gold(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Light light = (Light) o;
        return isIlluminate == light.isIlluminate
                && isHaseLight == light.isHaseLight
                && price.equals(light.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, isIlluminate, isHaseLight);
    }

    public boolean isIlluminate() {
        return isIlluminate;
    }

    public boolean isHaseLight() {
        return isHaseLight;
    }

    public String use() {
        if (isHaseLight) {
            if (isIlluminate) {
                isIlluminate = false;
                return "Light Is Turned Off";
            } else {
                isIlluminate = true;
                return "Light Is Turned On";
            }
        } else {
            return "There Is No game.world.portable.Light";
        }
    }

    @Override
    public int getPrice() {
        return price.getPrice();
    }

    @Override
    public String toString() {
        return "Light, price " + price.getPrice();
    }
}
