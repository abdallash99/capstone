package com.capstone.project.result;

import com.capstone.project.worldnavigator.Player;
import com.capstone.project.worldnavigator.world.item.Gold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    public List<Result> getResult(String username){
        return resultRepository.findAllByUsername(username);
    }

    public void saveResult(Result result){
        resultRepository.save(result);
    }

    public void findWinner(Map<String, Player> playerMap,String worldId){
        Gold maxGold=new Gold(0);
        Set<String> winnerUsername = getWinnerSet(playerMap, maxGold);
        saveResultToDB(playerMap, worldId, winnerUsername);
    }

    private void saveResultToDB(Map<String, Player> playerMap, String worldId, Set<String> winnerUsername) {
        for(var player: playerMap.entrySet()){
            final int score = player.getValue().getInv().getGold().getPrice();
            if(winnerUsername.contains(player.getKey())){
                saveResult(new Result(new IdClass(worldId,player.getKey()),"Winner", score));
            }else{
                saveResult(new Result(new IdClass(worldId,player.getKey()),"Loser",score));
            }
        }
    }

    private Set<String> getWinnerSet(Map<String, Player> playerMap, Gold maxGold) {
        Set<String> winnerUsername=new HashSet<>();
        for(var player: playerMap.entrySet()){
            if(player.getValue().getInv().getGold().getPrice()> maxGold.getPrice()){
                maxGold =player.getValue().getInv().getGold();
                winnerUsername.clear();
                winnerUsername.add(player.getKey());
            }else if(player.getValue().getInv().getGold().getPrice()== maxGold.getPrice()){
                winnerUsername.add(player.getKey());
            }
        }
        return winnerUsername;
    }
}
