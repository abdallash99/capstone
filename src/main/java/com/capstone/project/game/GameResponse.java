package com.capstone.project.game;

import com.capstone.project.worldnavigator.GameStatus;
import com.capstone.project.worldnavigator.world.portable.Gold;
import com.capstone.project.worldnavigator.world.portable.Portable;

import java.util.List;

public class GameResponse {
    private String res;
    private Gold numberOfGold;
    private List<Portable> items;
    private GameStatus isEnd;
    private boolean isDead;

    public GameResponse (Gold numberOfGold, List<Portable> items, GameStatus isEnd, boolean isDead) {
        this.numberOfGold = numberOfGold;
        this.items = items;
        this.isEnd = isEnd;
        this.isDead = isDead;
    }

    public GameStatus isEnd() {
        return isEnd;
    }

    public void setEnd(GameStatus end) {
        isEnd = end;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public Gold getNumberOfGold() {
        return numberOfGold;
    }

    public void setNumberOfGold(Gold numberOfGold) {
        this.numberOfGold = numberOfGold;
    }

    public List<Portable> getItems() {
        return items;
    }

    public void setItems(List<Portable> items) {
        this.items = items;
    }
}
