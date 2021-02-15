package com.capstone.project.worldnavigator;

import static com.capstone.project.ProjectApplication.*;
import com.capstone.project.worldnavigator.world.Map;
import com.capstone.project.worldnavigator.world.Room;
import com.capstone.project.worldnavigator.world.portable.Gold;
import com.capstone.project.worldnavigator.world.wall.Wall;

import java.awt.*;
import java.util.HashMap;

public class WorldNavigator {

    private final Map map;
    private final java.util.Map<String, Player> players;
    private final java.util.Map<Point, String> playersPosition;

    public WorldNavigator(Map map, java.util.Map<String, Player> players) {
        this.map = map;
        this.players = players;
        playersPosition =new HashMap<>();
        players.forEach((key,value)->playersPosition.put(value.getCurrentPosition(),key));
    }

    @Override
    public String toString() {
        return "WorldNavigator{" +
                "map=" + map +
                ", players=" + players +
                ", playersPosition=" + playersPosition +
                '}';
    }

    public java.util.Map<String, Player> getPlayers() {
        return players;
    }


    public String forward(String username) {
        final Player player = players.get(username);
        Point currentPosition = player.getCurrentPosition();
        int direction = player.getDirection();
        if (canMove(currentPosition, direction)) {
            final String move = player.move(direction);
            String res=checkNewRoom(username,currentPosition);
            setFrontWall(player);
            return move+"\n"+res;
        } else return "Front Is Blocked";
    }

    public String backward(String username) {
        final Player player = players.get(username);

        Point currentPosition = player.getCurrentPosition();
        int direction = player.getDirection();
        int newDirection = (direction + 2) % ROOM_WALL_NUMBER;

        if (canMove(currentPosition, newDirection)) {
            final String move = player.move(direction);
            String res=checkNewRoom(username,currentPosition);
            setFrontWall(player);
            return move+"\n"+res;
        }
        return "Back Is Blocked";
    }

    private boolean canMove(Point currentPosition, int direction) {
        Wall wall = map.getWall(currentPosition, direction);
        return !wall.getLock().isLocked() && !wall.getLock().isBlock() && !wall.getLock().isClosed();
    }
    public void setFrontWall(Player player) {
        Point currentPosition = player.getCurrentPosition();
        int direction = player.getDirection();
        player.setFrontWall(map.getWall(currentPosition, direction));
    }

    public String breakWall(String username) {
        Player player = players.get(username);
        player.getFrontWall().getLock().breakLock();
        player.getInv().add(new Gold(-13));
        return "Wall Broken You Lost 3 Gold";
    }

    private String checkNewRoom(String username,Point position) {
        Player ourPlayer=players.get(username);

        if(playersPosition.containsKey(position)){
            String anotherPlayerUsername= playersPosition.get(position);
            Player anotherPlayer= players.get(anotherPlayerUsername);
            Gold playerGold=anotherPlayer.getInv().getGold();
            Gold minusPlayerGold=new Gold( -playerGold.getPrice());

            anotherPlayer.getInv().add(minusPlayerGold);
            ourPlayer.getInv().addAll(anotherPlayer.getInv().check());
            addGoldToAllPlayer(playerGold);
            anotherPlayer.dead();
        }
        playersPosition.put(position,username);
        Room currentRoom =map.getRoom(position);
        ourPlayer.getInv().addAll(currentRoom.getInv().check());
        return currentRoom.getInv().loot() ;
    }

    public void exit(String username){
        Player player=players.get(username);
        Gold gold=player.getInv().getGold();
        Room room= map.getRoom(player.getCurrentPosition());
        room.getInv().addAll(player.getInv().check());
        player.dead();
        addGoldToAllPlayer(gold);
    }

    private void addGoldToAllPlayer(Gold playerGold) {
        int playerCount=1;
        if(players.size()!=1){
            playerCount=players.size()-1;
        }
        Gold gold=new Gold(playerGold.getPrice()/(playerCount));
        players.forEach((key,value)->value.getInv().add(gold));
    }

    public String playerStatus(String username) {
        return players.get(username).playerStatus();
    }


    public String switchLight(String username) {
        Point currentPosition = players.get(username).getCurrentPosition();
        return map.getLight(currentPosition).use();
    }

    public String open(String username) {
        return players.get(username).open();
    }

    public String right(String username) {
        final Player player = players.get(username);
        setFrontWall(player);
        return player.right();
    }

    public String left(String username) {
        final Player player = players.get(username);
        setFrontWall(player);
        return player.left();
    }

    public String useFlashLight(String username) {
        return players.get(username).useFlashLight();
    }

    public String useKey(String username, String keyColor) {
        return players.get(username).useKey(keyColor);
    }

    public String list(String username) {
        return players.get(username).list();
    }

    public String buy(String username, String name) {
        return players.get(username).buy(name);
    }

    public String sell(String username, String name) {
        return players.get(username).sell(name);
    }

    private boolean isNotDark(Player player) {
        return map.getLight(player.getCurrentPosition()).isIlluminate()
                || player.getFlashLight().isIlluminate();
    }

    public String look(String username) {
        final Player player = players.get(username);
        if (isNotDark(player)) {
            return player.look();
        } else return "Dark";
    }

    public String checkKey(String username) {
        return players.get(username).checkKey();
    }

    public String check(String username) {
        return players.get(username).check();
    }
}
