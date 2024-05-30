package com.rlgame;

import static com.rlgame.Globals.tileSize;

import com.rlgame.map.Room;

public class BooleanUtils {
    
    public static void printBoolMap(boolean[][] booleanMap) {
        int count = 0;
        for (boolean[] bool : booleanMap) {
            for (boolean p : bool) {
                if(p == true) {
                    System.out.print("#");
                }
                else{
                    System.out.print("%");
                }

            }
            System.out.println("-" + count);
            count++;
        }
        System.out.println();
    }

    public static boolean checkIfRoomFits(Room room, boolean[][] booleanMap) {
        boolean returnVal = true;
        int start_x,start_y;
        start_x = room.x()/tileSize;
        start_y = room.y()/tileSize;
        //System.out.println("x: "+start_x+"\ny: "+start_y);
        for (int x = start_x-1; x <= room.w() +start_x; x++) {
            for (int y = start_y-1; y <= room.h() +start_y; y++) {
                if(booleanMap[x][y]) {
               //     returnVal = true;
                }else{
                    returnVal = false;
                    break;
                }
                
            }
            if (!returnVal) {
                break;
            }
        }

        return returnVal; 
    }
}
