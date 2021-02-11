package com.capstone.project.worldnavigator;



import com.capstone.project.worldnavigator.util.Triple;
import com.capstone.project.worldnavigator.world.Room;
import com.capstone.project.worldnavigator.world.portable.*;
import com.capstone.project.worldnavigator.world.wall.Door;
import com.capstone.project.worldnavigator.world.wall.Wall;
import com.capstone.project.worldnavigator.world.wall.WallFactory;
import com.capstone.project.util.UtilFunctions;

import java.awt.*;
import java.util.*;
import java.util.List;

public class WorldNavigatorBuilder {

    private WorldNavigatorBuilder() {
    }

    private static final Random random = new Random();
    private static final int HEIGHT = random.nextInt(20) + 50;
    private static final int WIDTH = random.nextInt(20) + 50;
    private static final List<List<Room>> rooms = set();
    private static final List<Integer> x = getShuffledList(HEIGHT);
    private static final List<Integer> y = getShuffledList(WIDTH);
    private static final Map<Triple, Door> doors = new HashMap<>();
    private static final Queue<Key> keys = new LinkedList<>();
    private static int numberOfFlashlight = (int) (HEIGHT * WIDTH * 0.01);

    public static WorldNavigator build(Map<String,Player> players) {
        for (int row=0;row<HEIGHT;row++) {
            for (int col=0;col<WIDTH;col++) {
                rooms.get(row).set(col,createRoom(row,col));
            }
        }
        for (var row : x) {
            for (var col : y) {
                modifyRoom(rooms.get(row).get(col));
            }
        }
        com.capstone.project.worldnavigator.world.Map map = new com.capstone.project.worldnavigator.world.Map(rooms);
        final WorldNavigator worldNavigator = new WorldNavigator(map, players, GameStatus.NOT_STARTED);
        setPositions(players,worldNavigator);
        return worldNavigator;
    }

    private static void setPositions (Map<String, Player> players,WorldNavigator worldNavigator) {
        players.forEach((key,value)->{
            int x=random.nextInt(HEIGHT);
            int y=random.nextInt(WIDTH);
            int direction=random.nextInt(3);
            value.setCurrentPosition(new Point(x,y));
            value.setDirection(direction);
            worldNavigator.setFrontWall(value);
        });
    }

    private static void modifyRoom(Room room) {
        for(int i=0;i<4;i++){
            if(!(room.getWalls().get(i) instanceof Door)){
                room.getWalls().set(i,createRandomWall());
            }
        }
    }

    private static Key getKey() {
        String randomColor = UtilFunctions.generateRandomWords();
        return new Key(randomColor,new Gold(20));
    }



    private static List<Integer> getShuffledList(int max) {
        List<Integer> x = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            x.add(i);
        }
        Collections.shuffle(x);
        return x;
    }

    private static List<List<Room>> set() {
        List<List<Room>> lists = new ArrayList<>(HEIGHT);
        for (int i = 0; i < HEIGHT; ++i) {
            lists.add(new ArrayList<>());
            for (int j = 0; j < WIDTH; j++) {
                lists.get(i).add(null);
            }
        }
        return lists;
    }

    private static Room createRoom(int x,int y){
        List<Wall> walls = new ArrayList<>();
        walls.add(createDoor(new Triple(x,y,0)));
        walls.add(createDoor(new Triple(x,y,1)));
        walls.add(createDoor(new Triple(x,y,2)));
        walls.add(createDoor(new Triple(x,y,3)));
        return new Room(walls,random.nextBoolean());
    }

    private static Wall createDoor(Triple triple){
        int doorPer=random.nextInt(100);
        if(doors.containsKey(triple)){
            return doors.get(triple);
        } else if(doorPer>WallPercentage.DOOR_WITH_KEY.getMin()&&doorPer<=WallPercentage.DOOR_WITH_KEY.getMax()){
            return getDoor(triple);
        }else if(doorPer>WallPercentage.DOOR_WITHOUT_KEY.getMin()&&doorPer<=WallPercentage.DOOR_WITHOUT_KEY.getMax()){
            return  WallFactory.getWall("Door",new WithoutInv(),new WithLock(false));
        }else return WallFactory.getWall("Wall", new WithoutInv(), new WithoutLock(true));
    }
    private static Door getDoor(Triple triple) {
        Key key = getKey();
        keys.add(key);
        int direction= triple.getDirection()-1;
        if (direction < 0) direction = 3;
        direction--;
        if (direction < 0) direction = 3;
        int newX=WorldNavigator.MOVE_RATE[direction][0]+triple.getX();
        int newY=WorldNavigator.MOVE_RATE[direction][1]+ triple.getY();
        final Door door = (Door) WallFactory.getWall("Door", new WithoutInv(), new WithLock(key, true, false));
        doors.put(new Triple(newX,newY,direction),door);
        return door;
    }

    private static Wall createRandomWall() {
        double per=random.nextInt(100);
       if (per>WallPercentage.PAINTING.getMin()&&per<=WallPercentage.PAINTING.getMax()) {
           return getWallWithItem("Painting");
       } else if (per>WallPercentage.MIRROR.getMin()&&per<=WallPercentage.MIRROR.getMax()) {
           return getWallWithItem("Mirror");
       } else if (per>WallPercentage.SELLER.getMin()&&per<=WallPercentage.SELLER.getMax()){
           return getSeller();
        } else if (per>WallPercentage.CHEST.getMin()&&per<=WallPercentage.CHEST.getMax()) {
           return getChest();
       } else {
           return WallFactory.getWall("Wall", new WithoutInv(), new WithoutLock(true));
       }
    }

    private static Wall getSeller() {
        Inv inv = new Inv();
        if (!keys.isEmpty())
            inv.add(keys.poll());
        if (!keys.isEmpty())
            inv.add(keys.poll());
        if (numberOfFlashlight-- > 0)
            inv.add(new Light(true));
        return WallFactory.getWall("Seller", inv, new WithoutLock(true));
    }

    private static Wall getChest() {
        Inv inv = new Inv();
        if (!keys.isEmpty())
            inv.add(keys.poll());
        if (numberOfFlashlight-- > 0)
            inv.add(new Light(true));
        Key key = getKey();
        keys.add(key);
        return WallFactory.getWall("Chest", inv, new WithLock(key, true, true));
    }

    private static Wall getWallWithItem(String type) {
        Inv inv = new Inv();
        if (!keys.isEmpty())
            inv.add(keys.poll());
        if (numberOfFlashlight-- > 0)
            inv.add(new Light(true));
        return WallFactory.getWall(type, inv, new WithoutLock(true));
    }
}
