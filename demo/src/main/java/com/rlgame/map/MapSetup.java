package com.rlgame.map;

import static com.rlgame.Globals.mapSize;

import java.util.Random;

public class MapSetup {

    private GameMap[] gameMaps;
    private Random random;
    private int currentIndex = 0;

    public Random getRandom() {
        return random;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public MapSetup(int mapCount, Random rand) {
        random = rand;
        gameMaps = new GameMap[mapCount];
        for (int i = 0; i < gameMaps.length; i++) {
            gameMaps[i] = new GameMap(mapSize, random);

        }

        for (GameMap gameMap : gameMaps) {
            gameMap.startMap();
        }

    }

    public GameMap getCurrentMap() {
        return gameMaps[currentIndex];
    }

    public GameMap[] getGameMaps() {
        return gameMaps;
    }

    public void setGameMap(int x, int y) {
        if (x >= gameMaps.length) {
            x = 0;
        }
        currentIndex = x;
    }

}
