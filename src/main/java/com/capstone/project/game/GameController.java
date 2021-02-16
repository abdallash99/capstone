package com.capstone.project.game;

import com.capstone.project.exception.NotFoundException;
import com.capstone.project.exception.NotStartedException;
import com.capstone.project.worldnavigator.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class GameController {
  @Autowired private GameService gameService;

  @GetMapping("/query")
  public GameResponse query(
      @RequestParam(required = false, name = "requestType", defaultValue = "default")
          String requestType,
      Principal principal) {
    return gameService.query(requestType, principal.getName());
  }

  @GetMapping("/query_with_item")
  public GameResponse queryWithItem(
      @RequestParam(name = "requestType") String requestType,
      @RequestParam(name = "itemName") String itemName,
      Principal principal) {
    return gameService.queryWithItem(requestType, principal.getName(), itemName);
  }

  @GetMapping("/exit")
  public ResponseInfo exit(Principal principal) {
    gameService.exit(principal.getName());
    return new ResponseInfo("Exit Game successfully");
  }

  @GetMapping("/check")
  public ResponseInfo check(Principal principal) {
    if (gameService.checkGameStatus(principal.getName()).getWait()) {
      return new ResponseInfo("The Game Is Started");
    } else if (gameService.checkGameStatus(principal.getName()) == GameStatus.NOT_STARTED)
      throw new NotStartedException("The Game Not Started Yet");
    else throw new NotFoundException("You Not Join Game Yet");
  }

  @GetMapping("/join")
  public ResponseInfo join(Principal principal) {
    gameService.join(principal.getName());
    return new ResponseInfo("Join Successfully");
  }
}
