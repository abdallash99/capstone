package com.capstone.project;

import com.capstone.project.worldnavigator.DirectionsMove;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;


@SpringBootApplication
public class ProjectApplication {
    public static final Random random=new Random();
    public static final int HEIGHT = random.nextInt(20) + 50;
    public static final int WIDTH = random.nextInt(20) + 50;
    public static final int GAME_PERIOD = 300000;
    public static final int NUMBER_OF_PLAYER=2;
    public static final int ROOM_WALL_NUMBER = 4;
    public static final DirectionsMove[] MOVE_RATE = {
            DirectionsMove.UP,
            DirectionsMove.RIGHT,
            DirectionsMove.DOWN,
            DirectionsMove.LEFT
    };

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }


}
