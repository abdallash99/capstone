package com.capstone.project.worldnavigator.world.item;

public interface Openable {
    String use(Key key);

    String open();

    String check();

    boolean isLocked();

    boolean isBlock();

    boolean isClosed();

    void breakLock();
}
