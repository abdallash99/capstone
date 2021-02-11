package com.capstone.project.worldnavigator;


import com.capstone.project.worldnavigator.world.Map;
import com.capstone.project.worldnavigator.world.wall.Wall;

import java.awt.*;

public class WorldNavigator {
    public static final int ROOM_WALL = 4;
    protected static final int[][] MOVE_RATE = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private final Map map;
    private final java.util.Map<String, Player> players;
    private GameStatus status;

    public WorldNavigator(Map map, java.util.Map<String, Player> players, GameStatus status) {
        this.map = map;
        this.players = players;
        this.status=status;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
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
            setFrontWall(player);
            return move;
        } else return "Front Is Blocked";
    }

    public String playerStatus(String username) {
        return players.get(username).playerStatus();
    }

    private boolean canMove(Point currentPosition, int direction) {
        Wall wall = map.getWall(currentPosition, direction);
        return !wall.getLock().isLocked() && !wall.getLock().isBlock() && !wall.getLock().isClosed();
    }

    public String switchLight(String username) {
        Point currentPosition = players.get(username).getCurrentPosition();
        return map.getLight(currentPosition).use();
    }

    public String backward(String username) {
        final Player player = players.get(username);

        Point currentPosition = player.getCurrentPosition();
        int direction = player.getDirection();
        int newDirection = (direction + 2) % ROOM_WALL;

        if (canMove(currentPosition, newDirection)) {
            setFrontWall(player);
            return player.move(newDirection);
        }
        return "Back Is Blocked";
    }

    public void setFrontWall(Player player) {
        Point currentPosition = player.getCurrentPosition();
        int direction = player.getDirection();
        player.setFrontWall(map.getWall(currentPosition, direction));
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
