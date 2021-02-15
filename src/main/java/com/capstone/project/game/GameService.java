package com.capstone.project.game;
import static com.capstone.project.ProjectApplication.*;
import com.capstone.project.exception.BadRequestException;
import com.capstone.project.exception.ForbiddenException;
import com.capstone.project.result.ResultService;
import com.capstone.project.util.UtilFunctions;
import com.capstone.project.worldnavigator.GameStatus;
import com.capstone.project.worldnavigator.Player;
import com.capstone.project.worldnavigator.WorldNavigator;
import com.capstone.project.worldnavigator.WorldNavigatorBuilder;
import com.capstone.project.worldnavigator.world.portable.Gold;
import com.capstone.project.worldnavigator.world.portable.Portable;
import com.capstone.project.worldnavigator.world.portable.WithInv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final Map<String,WorldNavigator> worldNavigators;
    private final Map<String,String> playingPlayers;
    private final Map<String ,Date> startingTimeOfGame;
    private Map<String,Player> players;

    @Autowired
    private ResultService resultService;

    public GameService() {
        worldNavigators=Collections.synchronizedMap( new HashMap<>());
        players=Collections.synchronizedMap( new HashMap<>());
        playingPlayers=Collections.synchronizedMap( new HashMap<>());
        startingTimeOfGame=Collections.synchronizedMap( new HashMap<>());
    }

    public void join(String username){
        checkIfAlreadyJoined(username);
        WithInv withInv=new WithInv();
        withInv.add(new Gold(20));
        players.put(username,new Player(withInv));
        checkIfGameReady();
    }

    private void checkIfAlreadyJoined(String username) {
        if(playingPlayers.containsKey(username)){
            throw new BadRequestException("You are Already Join Game");
        }
    }

    private void addAllPlayers(String id) {
        players.forEach((key,value)->playingPlayers.put(key,id));
    }

    private void checkIfGameReady() {
        if(players.size()>=NUMBER_OF_PLAYER){
            String id= UtilFunctions.generateRandomWords();
            addAllPlayers(id);
            worldNavigators.put(id,WorldNavigatorBuilder.build(players));
            startingTimeOfGame.put(id,new Date());
            players=new HashMap<>();
        }
    }
    private void checkIfStart(String username) {
        if(!playingPlayers.containsKey(username))
            throw new ForbiddenException("The game not started yet");
    }

    public GameStatus checkGameStatus(String name) {
        if(playingPlayers.containsKey(name)){
            return GameStatus.START;
        }else if(players.containsKey(name)) return GameStatus.NOT_STARTED;
        else return GameStatus.NOT_JOINED;
    }
    private void checkIfEnd(String worldId) {
        Date now=new Date();
        if(now.getTime()-startingTimeOfGame.get(worldId).getTime()> GAME_PERIOD){
            Map<String,Player>playerMap= worldNavigators.get(worldId).getPlayers();
            resultService.findWinner(playerMap,worldId);
            playerMap.forEach((key,value)->playingPlayers.remove(key));
            worldNavigators.remove(worldId);
            throw new ForbiddenException("Game Is End");
        }
    }


    private GameResponse getResponse(String worldId, String username){
        checkIfEnd(worldId);
        checkIfStart(username);
        final WorldNavigator worldNavigator = worldNavigators.get(worldId);
        Player player= worldNavigator.getPlayers().get(username);
        Gold gold=player.getInv().getGold();
        List<Portable> portableList=player.getInv().getInvList();
        List<String> playersUsername=new ArrayList<>(worldNavigator.getPlayers().keySet());
        return new GameResponse(gold,portableList,playersUsername,player.isAlive());
    }



    public GameResponse query(String queryType, String username) {
        String worldId=playingPlayers.get(username);
        String res= switch (queryType){
             case "forward" -> forward(worldId, username);
             case "playerStatus" -> playerStatus(worldId, username);
             case "switchLight" -> switchLight(worldId, username);
             case "backward"-> backward(worldId, username);
             case "open"-> open(worldId, username);
             case "right"-> right(worldId, username);
             case "left"-> left(worldId, username);
             case "useFlashLight"-> useFlashLight(worldId, username);
             case "list"-> list(worldId, username);
             case "look"-> look(worldId, username);
             case "checkKey"-> checkKey(worldId, username);
             case "check"-> check(worldId, username);
             case "breakWall"-> breakWall(worldId, username);
             case "default"-> "";
             default -> throw new BadRequestException(String.format("unknown requestType : %s",queryType));
         };
        GameResponse gameResponse=getResponse(worldId,username);
        gameResponse.setRes(res);
        return gameResponse;
    }

    public GameResponse queryWithItem(String queryType,String username,String itemName){

        String worldId=playingPlayers.get(username);
        String res= switch (queryType){
            case "useKey"-> useKey(worldId,username,itemName);
            case "buy"-> buy(worldId,username,itemName);
            case "sell"-> sell(worldId,username,itemName);
            default -> throw new BadRequestException(String.format("unknown requestType : %s",queryType));
        };
        GameResponse gameResponse=getResponse(worldId,username);
        gameResponse.setRes(res);
        return gameResponse;
    }



    private String forward(String worldId,String username) {
        return worldNavigators.get(worldId).forward(username);
    }

    private String playerStatus(String worldId,String username) {
        return worldNavigators.get(worldId).playerStatus(username);
    }

    private String switchLight(String worldId,String username) {
        return worldNavigators.get(worldId).switchLight(username);
    }

    private String backward(String worldId,String username) {
        return worldNavigators.get(worldId).backward(username);
    }

    private String open(String worldId,String username) {
        return worldNavigators.get(worldId).open(username);
    }

    private String right(String worldId,String username) {
         return worldNavigators.get(worldId).right(username);

    }

    private String left(String worldId,String username) {
         return worldNavigators.get(worldId).left(username);
    }

    private String useFlashLight(String worldId,String username) {
        return worldNavigators.get(worldId).useFlashLight(username);
    }

    private String useKey(String worldId,String username, String keyColor) {
        return worldNavigators.get(worldId).useKey(username,keyColor);
    }

    private String list(String worldId,String username) {
        return worldNavigators.get(worldId).list(username);
    }

    private String buy(String worldId,String username, String name) {
        return worldNavigators.get(worldId).buy(username,name);
    }

    private String sell(String worldId,String username, String name) {
        return worldNavigators.get(worldId).sell(username,name);
    }

    private String look(String worldId,String username) {
        return worldNavigators.get(worldId).look(username);
    }

    private String checkKey(String worldId,String username) {
        return worldNavigators.get(worldId).checkKey(username);
    }

    private String check(String worldId,String username) {
        return worldNavigators.get(worldId).check(username);
    }

    private String breakWall(String worldId,String username) {
        return worldNavigators.get(worldId).breakWall(username);
    }


    public void exit(String name) {
        if(playingPlayers.containsKey(name)){
            String worldId= playingPlayers.get(name);
            WorldNavigator worldNavigator= worldNavigators.get(worldId);
            worldNavigator.exit(name);
            playingPlayers.remove(name);
            worldNavigator.getPlayers().remove(name);
        }
        players.remove(name);
    }
}
