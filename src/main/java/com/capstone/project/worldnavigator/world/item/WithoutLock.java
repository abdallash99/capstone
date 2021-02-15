package com.capstone.project.worldnavigator.world.item;

public class WithoutLock implements Openable {
    private boolean isBlock;

    public WithoutLock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    @Override
    public String use(Key key) {
        return "Can't use the key in non lock wall";
    }

    @Override
    public String open() {
        return "Can't open this wall";
    }

    @Override
    public String check() {
        return "Can't check lock for wall without lock";
    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public boolean isBlock() {
        return isBlock;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void breakLock() {
        isBlock=false;
    }
}
