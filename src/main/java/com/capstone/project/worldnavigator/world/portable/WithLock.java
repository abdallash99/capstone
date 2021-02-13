package com.capstone.project.worldnavigator.world.portable;


public class WithLock implements Openable {
    private boolean isBlock;
    private final Key key;
    private boolean isLocked;
    private boolean isClosed;

    public WithLock(Key key, boolean isLocked, boolean isBlock) {
        this.key = key;
        this.isLocked = isLocked;
        this.isClosed = true;
        this.isBlock = isBlock;
    }

    public WithLock(boolean isBlock) {
        this.key = new Key();
        this.isLocked = false;
        this.isClosed = true;
        this.isBlock = isBlock;
    }

    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public String use(Key key) {
        if (this.key.equals(key)) {
            isLocked = false;
            return "Unlocked Successfully";
        } else return "This key is not suitable for the lock";
    }

    @Override
    public String open() {
        if (isClosed && !isLocked) {
            isClosed = false;
            return "Opened Successfully";
        } else if (isLocked) {
            return "Locked " + key.getKeyType() + " key is required To Open";
        } else {
            return "Already opened";
        }
    }

    @Override
    public String check() {
        if (isLocked) return "locked, " + key.getKeyType() + " key is needed to unlock";
        else return "opened";
    }

    @Override
    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public void breakLock() {
        isLocked=false;
        isBlock=false;
        isClosed=false;
    }

    @Override
    public boolean isBlock() {
        return isBlock;
    }
}

