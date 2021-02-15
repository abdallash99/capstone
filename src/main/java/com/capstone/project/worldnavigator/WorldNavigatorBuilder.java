package com.capstone.project.worldnavigator;


import static com.capstone.project.ProjectApplication.*;

import com.capstone.project.util.Triple;
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

    private WorldNavigatorBuilder() { }


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
        final WorldNavigator worldNavigator = new WorldNavigator(map, players);
        setPositions(players,worldNavigator);
        return worldNavigator;
    }

    private static void setPositions (Map<String, Player> players,WorldNavigator worldNavigator) {
        players.forEach((key,value)->{
            int x=random.nextInt(HEIGHT-1);
            int y=random.nextInt(WIDTH-1);
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
        return new Key(randomColor,new Gold(10));
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
        List<List<Room>> lists = Collections.synchronizedList(new ArrayList<>(HEIGHT));
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
        return new Room(walls,new Light(random.nextBoolean()),new WithInv());
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
        int newX=MOVE_RATE[direction].getX()+triple.getX();
        int newY=MOVE_RATE[direction].getY()+ triple.getY();
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
        WithInv withInv = new WithInv();
        if (!keys.isEmpty())
            withInv.add(keys.poll());
        if (!keys.isEmpty())
            withInv.add(keys.poll());
        if (numberOfFlashlight-- > 0)
            withInv.add(new Light(new Gold(2),false,true));
        return WallFactory.getWall("Seller", withInv, new WithoutLock(true));
    }

    private static Wall getChest() {
        WithInv withInv = new WithInv();
        if (!keys.isEmpty())
            withInv.add(keys.poll());
        if (numberOfFlashlight-- > 0)
            withInv.add(new Light(new Gold(2),false,true));
        Key key = getKey();
        keys.add(key);
        return WallFactory.getWall("Chest", withInv, new WithLock(key, true, true));
    }

    private static Wall getWallWithItem(String type) {
        WithInv withInv = new WithInv();
        if (!keys.isEmpty())
            withInv.add(keys.poll());
        if (numberOfFlashlight-- > 0)
            withInv.add(new Light(new Gold(2),false,true));
        return WallFactory.getWall(type, withInv, new WithoutLock(true));
    }
}
