package com.capstone.project.game;

import com.capstone.project.worldnavigator.world.portable.Gold;
import com.capstone.project.worldnavigator.world.portable.Portable;

import java.util.List;

public class GameResponse {
    private String res;
    private Gold numberOfGold;
    private List<Portable> items;
    private List<String> playersName;
    private boolean isDead;

    public GameResponse( Gold numberOfGold, List<Portable> items, List<String> playersName, boolean isDead) {
        this.numberOfGold = numberOfGold;
        this.items = items;
        this.playersName = playersName;
        this.isDead = isDead;
    }

    public List<String> getPlayersName() {
        return playersName;
    }

    public void setPlayersName(List<String> playersName) {
        this.playersName = playersName;
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
