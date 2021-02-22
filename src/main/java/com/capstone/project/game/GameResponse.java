package com.capstone.project.game;

import com.capstone.project.worldnavigator.world.item.Gold;
import com.capstone.project.worldnavigator.world.item.Portable;

import java.util.List;

public class GameResponse {
    private String res;
    private Gold numberOfGold;
    private List<Portable> items;
    private List<String> playersName;

    @Override
    public String toString() {
        return "GameResponse{" +
                "res='" + res + '\'' +
                ", numberOfGold=" + numberOfGold +
                ", items=" + items +
                ", playersName=" + playersName +
                '}';
    }

    public GameResponse(Gold numberOfGold, List<Portable> items, List<String> playersName) {
        this.numberOfGold = numberOfGold;
        this.items = items;
        this.playersName = playersName;
    }

    public List<String> getPlayersName() {
        return playersName;
    }

    public void setPlayersName(List<String> playersName) {
        this.playersName = playersName;
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
