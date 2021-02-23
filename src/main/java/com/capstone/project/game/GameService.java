package com.capstone.project.game;
import static com.capstone.project.ProjectApplication.*;

import com.capstone.project.exception.BadRequestException;
import com.capstone.project.exception.ForbiddenException;
import com.capstone.project.result.ResultService;
import com.capstone.project.util.RoomKey;
import com.capstone.project.worldnavigator.GameStatus;
import com.capstone.project.worldnavigator.Player;
import com.capstone.project.worldnavigator.world.Room;
import com.capstone.project.worldnavigator.world.item.*;
import com.capstone.project.worldnavigator.world.wall.Wall;
import org.redisson.api.RMap;
import org.redisson.api.RMultimap;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
public class GameService {

    private final RMap<RoomKey, Room> rooms;
    private final RMap<String,Player> playingPlayers;
    private final RMap<String,GameStatus> playerStatus;
    private final RMap<RoomKey ,String> playerPositions;
    private final RMap<String ,Date> startingTimeOfGame;
    private final RMultimap<String,String> playerInGame;
    private final RQueue<String> waitingPlayer;

    private final ResultService resultService;

    private final RedissonClient redissonClient;
    @Autowired
    public GameService(RedissonClient redissonClient,ResultService resultService) {
      rooms=redissonClient.getMap("rooms");
      playingPlayers=redissonClient.getMap("players");
      startingTimeOfGame=redissonClient.getMap("time");
      playerPositions =redissonClient.getMap("positions");
      playerStatus=redissonClient.getMap("status");
      waitingPlayer=redissonClient.getQueue("playerQueue");
      playerInGame=redissonClient.getListMultimap("playerInGame");
      this.resultService=resultService;
      this.redissonClient=redissonClient;
    }

    public void join(String username){
        checkIfAlreadyJoined(username);
        waitingPlayer.add(username);
        playerStatus.put(username,GameStatus.NOT_STARTED);
    }

    private void checkIfAlreadyJoined(String username) {
        if(playerStatus.containsKey(username)){
            throw new BadRequestException("You are Already Join Game");
        }
    }



    private void checkIfStart(String username) {
        if(!playingPlayers.containsKey(username))
            throw new ForbiddenException("The game not started yet");
    }

    public GameStatus checkGameStatus(String username) {
        if(playerStatus.containsKey(username))
            return playerStatus.get(username);
        throw new ForbiddenException("You Are Not Join Any Game");
    }
    private void checkIfEnd(String worldId) {
        Date now=new Date();
        if(now.getTime()-startingTimeOfGame.get(worldId).getTime()> GAME_PERIOD){
            Collection<String> playerUsername= playerInGame.get(worldId);
            Map<String,Player> players=new HashMap<>();
            for (var username:playerUsername){
                players.put(username,playingPlayers.get(username));
            }
            resultService.findWinner(players,worldId);
            deletePlayers(playerUsername);

            deleteRooms(worldId);
            throw new ForbiddenException("Game Is End");
        }
    }

    private void deletePlayers( Collection<String> playerUsername) {
        for (var username: playerUsername){
            playerStatus.remove(username);
            playingPlayers.remove(username);
        }
    }

    private void deleteRooms(String worldId) {
        for(int row=0;row< HEIGHT;row++){
            for (int col=0;col<WIDTH;col++){
                rooms.remove(new RoomKey(worldId,row,col));
            }
        }
    }


    private GameResponse getResponse(String worldId, String username){
        checkIfEnd(worldId);
        checkIfStart(username);
        Player player= playingPlayers.get(username);
        Gold gold=player.getInv().getGold();
        List<Portable> portableList=player.getInv().getInvList();
        List<String> playersUsername=new ArrayList<>(playerInGame.get(worldId));
        return new GameResponse(gold,portableList,playersUsername);
    }



    public GameResponse query(String queryType, String username) {
        String worldId=playingPlayers.get(username).getWorldId();
        String res= switch (queryType){
             case "forward" -> forward(username);
             case "playerStatus" -> playerStatus(username);
             case "switchLight" -> switchLight(username);
             case "backward"-> backward(username);
             case "open"-> open(username);
             case "right"-> right( username);
             case "left"-> left(username);
             case "useFlashLight"-> useFlashLight(username);
             case "list"-> list(username);
             case "look"-> look(username);
             case "checkKey"-> checkKey(username);
             case "check"-> check(username);
             case "breakWall"-> breakWall(username);
             case "default"-> "";
             default -> throw new BadRequestException(String.format("unknown requestType : %s",queryType));
         };
        GameResponse gameResponse=getResponse(worldId,username);
        gameResponse.setRes(res);
        return gameResponse;
    }

    public GameResponse queryWithItem(String queryType,String username,String itemName){

        String worldId=playingPlayers.get(username).getWorldId();
        String res= switch (queryType){
            case "useKey"-> useKey(username,itemName);
            case "buy"-> buy(username,itemName);
            case "sell"-> sell(username,itemName);
            default -> throw new BadRequestException(String.format("unknown requestType : %s",queryType));
        };
        GameResponse gameResponse=getResponse(worldId,username);
        gameResponse.setRes(res);
        return gameResponse;
    }



    public String forward(String username) {
        final Player player = playingPlayers.get(username);
        Point currentPosition = player.getCurrentPosition();
        int direction = player.getDirection();
        if (canMove(player,direction)) {
            final String move = player.move(direction);
            String res=checkNewRoom(username,currentPosition);
            return move+"\n"+res;
        } else return "Front Is Blocked";
    }

    public String backward(String username) {
        final Player player = playingPlayers.get(username);

        Point currentPosition = player.getCurrentPosition();
        int direction = player.getDirection();
        int newDirection = (direction + 2) % ROOM_WALL_NUMBER;

        if (canMove(player,newDirection)) {
            final String move = player.move(direction);
            String res=checkNewRoom(username,currentPosition);
            return move+"\n"+res;
        }
        return "Back Is Blocked";
    }

    private boolean canMove(Player player,int direction) {
        Wall wall=getFrontWall(player,direction);
        return !wall.getLock().isLocked() && !wall.getLock().isBlock() && !wall.getLock().isClosed();
    }

    private Wall getFrontWall(Player player,int direction){
        Room currentRoom = rooms.get(new RoomKey(player.getWorldId(),
                player.getCurrentPosition().x,
                player.getCurrentPosition().y));
        return currentRoom.getWalls().get(direction);
    }

    public String breakWall(String username) {
        Player player = playingPlayers.get(username);
        getFrontWall(player,player.getDirection()).getLock().breakLock();
        player.getInv().add(new Gold(-13));
        return "Wall Broken You Lost 3 Gold";
    }

    private String checkNewRoom(String username,Point position) {
        Player ourPlayer=playingPlayers.get(username);

        final RoomKey roomKey = new RoomKey(ourPlayer.getWorldId(), ourPlayer.getCurrentPosition().x, ourPlayer.getCurrentPosition().y);

        if(playerPositions.containsKey(roomKey)){
            String anotherPlayerUsername= playerPositions.get(roomKey);
            Player anotherPlayer= playingPlayers.get(anotherPlayerUsername);
            Gold playerGold=anotherPlayer.getInv().getGold();
            Gold minusPlayerGold=new Gold( -playerGold.getPrice());

            anotherPlayer.getInv().add(minusPlayerGold);
            ourPlayer.getInv().addAll(anotherPlayer.getInv().check());
            addGoldToAllPlayer(playerGold,ourPlayer.getWorldId());
            playingPlayers.remove(anotherPlayerUsername);
            playerStatus.remove(anotherPlayerUsername);
        }
        playerPositions.put(new RoomKey(ourPlayer.getWorldId(),position.x,position.y),username);
        Room currentRoom =rooms.get(getRoomKey(ourPlayer));
        ourPlayer.getInv().addAll(currentRoom.getInv().check());
        return currentRoom.getInv().loot() ;
    }

    public void addPlayerItemsToWorld(String username){
        Player player=playingPlayers.get(username);
        Gold gold=player.getInv().getGold();
        Room room= rooms.get(getRoomKey(player));
        room.getInv().addAll(player.getInv().check());
        addGoldToAllPlayer(gold,player.getWorldId());
    }

    private void addGoldToAllPlayer(Gold playerGold,String worldId) {
        int playerCount=1;
        Collection<String> players=playerInGame.get(worldId);
        if(players.size()>1){
            playerCount=players.size()-1;
        }
        Gold gold=new Gold(playerGold.getPrice()/(playerCount));
        players.forEach(value->playingPlayers.get(value).getInv().add(gold));
    }

    public String playerStatus(String username) {
        return playingPlayers.get(username).playerStatus();
    }


    public String switchLight(String username) {
        Player player = playingPlayers.get(username);
        return rooms.get(getRoomKey(player)).getLight().use();
    }

    public String open(String username) {
        final Player player = playingPlayers.get(username);
        return getFrontWall(player,player.getDirection()).getLock().open();
    }

    public String right(String username) {
        final Player player = playingPlayers.get(username);
        return player.right();
    }

    public String left(String username) {
        final Player player = playingPlayers.get(username);
        return player.left();
    }

    public String useFlashLight(String username) {
        return playingPlayers.get(username).useFlashLight();
    }

    public String useKey(String username, String keyColor) {
        final Player player = playingPlayers.get(username);
        return getFrontWall(player,player.getDirection()).unlock(new Key(keyColor));
    }

    public String list(String username) {
        final Player player = playingPlayers.get(username);
        return getFrontWall(player,player.getDirection()).getInv().list();
    }

    public String buy(String username, String name) {
        final Player player = playingPlayers.get(username);
        WithInv playerInv= player.getInv();
        Inventory wallInv=getFrontWall(player,player.getDirection()).getInv();
        wallInv.sell(name);
        return playerInv.buy(name);
    }

    public String sell(String username, String name) {
        final Player player = playingPlayers.get(username);
        WithInv playerInv= player.getInv();
        Inventory wallInv=getFrontWall(player,player.getDirection()).getInv();
        wallInv.buy(name);
        return playerInv.sell(name);
    }

    private boolean isNotDark(Player player) {
        Light roomLight= rooms.get(getRoomKey(player)).getLight();
        return roomLight.isIlluminate()||player.getInv().getLight().isIlluminate();
    }

    public String look(String username) {
        final Player player = playingPlayers.get(username);
        if (isNotDark(player)) {
            return getFrontWall(player,player.getDirection()).look();
        } else return "Dark";
    }

    public String checkKey(String username) {
        final Player player = playingPlayers.get(username);
        Wall frontWall= getFrontWall(player,player.getDirection());
        return frontWall.getLock().check();
    }

    public String check(String username) {
        final Player player = playingPlayers.get(username);
        Wall frontWall= getFrontWall(player,player.getDirection());
            if (frontWall.getLock().isLocked()) {
                return frontWall.getLock().check();
            } else if (!frontWall.getInv().check().isEmpty()) {
                player.getInv().addAll(frontWall.getInv().check());
                return frontWall.getInv().loot();
            }
            return "No Item Here";
    }

    public RoomKey getRoomKey(Player player){
        return new RoomKey(player.getWorldId(),player.getCurrentPosition().x,player.getCurrentPosition().y);
    }

    public void exit(String name) {
        if(playingPlayers.containsKey(name)){
            addPlayerItemsToWorld(name);
            Player player= playingPlayers.get(name);
            playerPositions.remove(getRoomKey(player));
            playingPlayers.remove(name);
        }
        playerStatus.remove(name);
    }
}
