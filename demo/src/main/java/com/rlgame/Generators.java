package com.rlgame;
import static com.rlgame.Globals.tileSize;

import java.util.ArrayList;

public class Generators {
    
    public ArrayList<Point> genRoom(int whith, int height) {
        ArrayList<Point> room = new ArrayList<>();

        return room;
    }

    public static ArrayList<Point> genMap(int size) {
        ArrayList<Point> map = new ArrayList<>();
        for (int row = 0; row <= size; row++) {
            for (int col = 0; col <= size; col++) {
                Point point = new Point(/*tileSize/2+*/row*(tileSize), /*tileSize/2+*/col*(tileSize));
                map.add(point);
            }
        }
        return map;
    }

}
