package com.capstone.project.worldnavigator.world.wall;

import com.capstone.project.worldnavigator.world.portable.*;

public abstract class Wall {
    private final Openable lock;
    private final Inventory inv;

    protected Wall(Openable lock, Inventory inv) {
        this.lock = lock;
        this.inv = inv;
    }

    protected Wall(Openable lock) {
        this.lock = lock;
        this.inv = new WithoutInv();
    }

    protected Wall(Inventory inv) {
        this.inv = inv;
        this.lock = new WithoutLock(true);
    }

    protected Wall() {
        this.lock = new WithoutLock(true);
        this.inv = new WithoutInv();
    }

    public abstract String look();

    public String unlock(Key key) {
        return lock.use(key);
    }

    public Openable getLock() {
        return lock;
    }

    public Inventory getInv() {
        return inv;
    }
}
