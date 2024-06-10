package com.rlgame.map;

import static com.rlgame.Globals.tileSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.rlgame.Point;

public class Generators {

    boolean awesomeSaws = true;

    public static boolean[][] flood(Point p, boolean[][] booleanMap) {
        boolean[][] bMap = booleanMap;
        int i = p.x();
        int j = p.y();

        if (i >= bMap.length || j >= bMap.length) {
            return bMap;
        } else if (i == -1 || j == -1) {
            return bMap;

        } else if (!bMap[i][j]) {
            bMap[i][j] = true;
            bMap = flood(new Point(i + 1, j), bMap);
            bMap = flood(new Point(i - 1, j), bMap);
            bMap = flood(new Point(i, j - 1), bMap);
            bMap = flood(new Point(i, j + 1), bMap);

            return bMap;
        } else {
            return bMap;
        }

    }

    public static boolean[][] placeTunnels2(ArrayList<Room> rooms, boolean[][] booleanMap, Random ran) {

        for (int i = 0; i < rooms.size(); i++) {
            Room room1 = rooms.get(i);
            for (int j = 0; j < rooms.size(); j++) {
                Room room2 = rooms.get(j);

                if (room1.equals(room2)) {
                } else {

                    booleanMap = carvTunnel(room1, room2, booleanMap);
                }
            }
        }
        return booleanMap;

    }

    private static boolean[][] carvTunnel(Room room1, Room room2, boolean[][] booleanMap) {

        if (room1.x() + room1.w() == room2.x() - 1 && room1.y() == room2.y()) {
            room1.addConnection(room2);
            room2.addConnection(room1);
            for (int i = room1.x(); i < room2.x() + room2.w(); i++) {
                booleanMap[i][room2.y() + 1] = false;
            }

        } else if (room2.x() + room2.w() == room1.x() - 1 && room1.y() == room2.y()) {
            room1.addConnection(room2);
            room2.addConnection(room1);

            for (int i = room2.x(); i < room1.x() + room1.w(); i++) {
                booleanMap[i][room1.y() + 1] = false;
            }

        } else if (room1.y() + room1.h() == room2.y() - 1 && room1.x() == room2.x()) {
            room1.addConnection(room2);
            room2.addConnection(room1);

            for (int i = room1.y(); i < room2.y() + room2.h(); i++) {
                booleanMap[room2.x() + 1][i] = false;
            }

        } else if (room2.y() + room2.h() == room1.y() - 1 && room1.x() == room2.x()) {
            room1.addConnection(room2);
            room2.addConnection(room1);

            for (int i = room2.y(); i < room1.y() + room1.h(); i++) {
                booleanMap[room1.x() + 1][i] = false;
            }

        } else {

        }
        return booleanMap;

    }

    public static ArrayList<Point> genMap(int size) {
        ArrayList<Point> map = new ArrayList<>();
        for (int row = 0; row <= size; row++) {
            for (int col = 0; col <= size; col++) {
                Point point = new Point(/* tileSize/2+ */row * (tileSize), /* tileSize/2+ */col * (tileSize));
                map.add(point);
            }
        }
        return map;
    }

    public static boolean[][] placeTunnels(ArrayList<Room> rooms, boolean[][] booleanMap, Random ran) {

        boolean[][] bMap = new boolean[booleanMap.length][booleanMap.length];
        for (int i = 0; i < bMap.length; i++) {
            for (int j = 0; j < bMap.length; j++) {
                bMap[i][j] = true;
                if (!booleanMap[i][j]) {
                    bMap[i][j] = false;
                }
            }
        }

        boolean swap = false;
        List<Integer> potentialRooms = null;
        for (Room room : rooms) {
            room.connecetRooms.clear();
        }
        for (int room = 0; room < rooms.size() * 2; room++) {
            Room room1 = null;
            Room room2 = null;
            int index = room % rooms.size();
            room1 = rooms.get(index);
            int x1 = ((room1.x() / tileSize) * 2 + room1.w()) / 2;
            int y1 = ((room1.y() / tileSize) * 2 + room1.h()) / 2;

            potentialRooms = rooms.stream().map(a -> (int) (Math.sqrt(
                    Math.abs(
                            Math.pow((((a.x() / (double) tileSize) * 2.0 + a.w()) / 2.0) - x1, 2.0)) +
                            Math.abs(
                                    Math.pow((((a.y() / (double) tileSize) * 2.0 + a.h()) / 2.0) - y1, 2.0))))

            )
                    .collect(Collectors.toList());
            potentialRooms = potentialRooms.stream().filter(a -> a > 0).toList();
            int index2 = potentialRooms.stream().min(Integer::compare).get();
            room2 = rooms.get(potentialRooms.indexOf(index2));
            boolean tag = true;
            int countMe = 0;
            while (tag == true) {
                if (room1.getRooms().contains(room2) && room2.getRooms().contains(room1)) {
                    for (Room r2 : rooms) {
                        if (!r2.getRooms().contains(room1) &&
                                !room1.getRooms().contains(r2))
                            if (r2.getRooms().size() < 2) {
                                room2 = r2;
                                tag = false;
                                break;
                            }
                    }
                } else {
                    tag = false;
                }
                if (countMe >= 5) {
                    tag = false;

                }
                countMe++;
            }

            if (!room1.getRooms().contains(room2) || !room2.getRooms().contains(room1)) {

                int x2 = (room2.x() / tileSize) * 2 + room2.w();
                int y2 = (room2.y() / tileSize) * 2 + room2.h();
                room1.addConnection(room2);
                room2.addConnection(room1);

                x2 = x2 / 2;
                y2 = y2 / 2;

                int wx1, wx2, wy1, wy2;
                wx1 = x1;
                wx2 = x2;
                wy1 = y1;
                wy2 = y2;
                if (x2 < x1 || y2 < y1) {
                    wx1 = x2;
                    wx2 = x1;
                    wy1 = y2;
                    wy2 = y1;
                    swap = false;
                }
                if (swap) {
                    for (int i = wx1; i <= wx2; i++) {
                        bMap[i][wy2] = false;
                    }
                    for (int j = wy1; j <= wy2; j++) {
                        bMap[wx1][j] = false;
                    }
                    swap = false;
                } else {
                    for (int i = wx1; i <= wx2; i++) {
                        bMap[i][wy1] = false;
                    }
                    for (int j = wy1; j <= wy2; j++) {
                        bMap[wx2][j] = false;
                    }
                    swap = true;

                }
            }
        }
        return bMap;
    }

    public static boolean mapValidator(boolean[][] mapToValidate) {
        boolean[][] bMap = new boolean[mapToValidate.length][mapToValidate.length];
        for (int i = 0; i < bMap.length; i++) {
            for (int j = 0; j < bMap.length; j++) {
                bMap[i][j] = true;
                if (!mapToValidate[i][j]) {
                    bMap[i][j] = false;
                }
            }
        }
        Point starPoint = new Point(2, 2);
        bMap = flood(starPoint, bMap);

        boolean breakVar = false;
        for (boolean[] bs : bMap) {
            for (boolean bs2 : bs) {
                if (!bs2) {
                    breakVar = true;
                    break;
                }
            }
        }
        if (breakVar) {
            return false;
        }

        return true;

    }

    public static boolean[][] placeTunnel(Room room1, Room room2, boolean[][] booleanMap, Random ran) {

        if (room1.x() == room2.x()) {
            int yStart = 0;
            int yEnt = 0;
            if (room1.y() <= room2.y()) {
                yStart = room1.y();
                yEnt = room2.y();
            } else {
                yStart = room2.y();
                yEnt = room1.y();
            }
            for (int i = yStart; i < yEnt; i++) {
                booleanMap[room1.x() + 1][i] = false;
            }
        } else if (room1.y() == room2.y()) {
            int xStart = 0;
            int xEnt = 0;
            if (room1.x() <= room2.x()) {
                xStart = room1.x();
                xEnt = room2.x();
            } else {
                xStart = room2.x();
                xEnt = room1.x();
            }
            for (int i = xStart; i < xEnt; i++) {
                booleanMap[i][room1.y() + 1] = false;
            }
        } else if (room1.y() != room2.y() && room1.x() != room2.x()) {
            assert room1.y() != room2.y() && room1.x() != room2.x() : "fack";

            int yStart = 0;
            int yEnt = 0;
            int xStart = 0;
            int xEnt = 0;
            if (room1.x() <= room2.x()) {
                xStart = room1.x();
                xEnt = room2.x();
            } else {
                xStart = room2.x();
                xEnt = room1.x();
            }
            for (int i = xStart; i < xEnt + 1; i++) {
                booleanMap[i][room1.y() + 1] = false;
            }
            if (room1.y() <= room2.y()) {
                yStart = room1.y();
                yEnt = room2.y();
            } else {
                yStart = room2.y();
                yEnt = room1.y();
            }
            for (int i = yStart + 1; i < yEnt; i++) {
                booleanMap[room2.x() + 1][i] = false;
            }

        } else {

            System.exit(0);
        }

        room1.addConnection(room2);
        room2.addConnection(room1);

        return booleanMap;
    }

    public static Room findClosest(Room room1, ArrayList<Room> rooms, Random ran) {
        Room tempRoom = null;
        for (Room room : rooms) {
            if (!room.equals(room1)) {
                if (tempRoom == null) {
                    tempRoom = room;
                } else if (pitagras(room1, room) <= pitagras(room1, tempRoom) && !room1.connecetRooms.contains(room)) {
                    tempRoom = room;
                }

            }
        }

        return tempRoom;

    }

    private static Double pitagras(Room room1, Room room2) {

        Double reVal = Math
                .sqrt(Math.pow(Math.abs(room1.x() + (room1.w() / 2) - room2.x() + (room2.w() / 2)), 2)
                        + Math.pow(Math.abs(room1.y() + (room1.h() / 2) - room2.y() + (room2.h() / 2)), 2));
        return reVal;
    }

}
