package com.rlgame.map;

import static com.rlgame.Globals.mapSize;

import java.util.Random;

public class MapSetup {
    

    private GameMap[] gameMaps;
    private Random random;
    private int currentIndex = 0;
    
    public MapSetup(int mapCount, Random rand) {
        random = rand;
        gameMaps = new GameMap[mapCount];
        gameMaps[0] = new GameMap(mapSize,random);
        gameMaps[1] = new GameMap(mapSize,random);
               
        for (GameMap gameMap : gameMaps) {
            gameMap.startMap();
        }
        
    }

    public GameMap getCurrentMap(){
        return gameMaps[currentIndex];
    }

    public GameMap[] getGameMaps() {
        return gameMaps;
    }

}
