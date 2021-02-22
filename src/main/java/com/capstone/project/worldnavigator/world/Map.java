package com.capstone.project.worldnavigator.world;


import com.capstone.project.exception.BadRequestException;
import com.capstone.project.util.RoomKey;
import org.redisson.api.RMap;

import java.awt.*;

public class Map {
    private final RMap<RoomKey,Room> rooms;

    public Map(RMap<RoomKey,Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Map{" +
                "rooms=" + rooms +
                '}';
    }

    public Room getRoom(Point currentPosition,String worldId) {
        final RoomKey roomKey = new RoomKey(worldId, currentPosition.x, currentPosition.y);
        if(rooms.containsKey(roomKey)){
            return rooms.get(roomKey);
        }else throw new BadRequestException("There is No Room With This id");
    }

}
