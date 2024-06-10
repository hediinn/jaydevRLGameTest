package com.rlgame;

import static com.rlgame.Globals.tileSize;

import com.rlgame.map.Room;

public class BooleanUtils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void printBoolMap(boolean[][] booleanMap) {
        int count = 0;
        System.out.println("01234567890123456789012");
        System.out.println("-----------------------");
        for (boolean[] bool : booleanMap) {
            for (boolean p : bool) {
                if (p == true) {
                    System.out.print(ANSI_GREEN + "#" + ANSI_RESET);
                } else {
                    System.out.print("%");
                }

            }
            System.out.println("-" + count);
            count++;
        }
        System.out.println();
    }

    public static boolean roomCross(Room room1, Room room2) {
        return ((room1.x() < room2.x() + room2.w() &&
                room1.x() + room1.w() > room2.x()) &&
                (room1.y() < room2.y() + room2.h() &&
                        room1.y() + room1.h() > room2.y()));
    }

    public static boolean checkIfRoomFits(Room room, boolean[][] booleanMap) {
        boolean returnVal = true;
        int start_x, start_y;
        start_x = room.x() / tileSize;
        start_y = room.y() / tileSize;
        // System.out.println("x: "+start_x+"\ny: "+start_y);
        for (int x = start_x - 1; x <= room.w() + start_x; x++) {
            for (int y = start_y - 1; y <= room.h() + start_y; y++) {
                if (booleanMap[x][y]) {
                    // returnVal = true;
                } else {
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
