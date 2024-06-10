package com.rlgame.map;

import java.util.List;
import java.util.Random;

import java.util.ArrayList;

import static com.rlgame.Globals.mapSize;
import static com.rlgame.Globals.tileSize;

import com.raylib.Jaylib.Vector2;
import com.rlgame.BooleanUtils;
import com.rlgame.Point;
import com.rlgame.entities.Entity;
import com.rlgame.entities.WallEnt;

import static com.raylib.Jaylib.*;

public class GameMap {

    private ArrayList<Point> mapPoints;
    private boolean[][] booleanMap;
    private List<Entity> entities;
    private Point unUsed = null;
    // private int seed = 0; green seed
    private Random ran;
    private int roomCount;
    private int MAX = 7;
    private boolean allRoomsConnected = false;

    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Room> segments = new ArrayList<>();

    public GameMap(int size, Random ra) {
        ran = ra;
        roomCount = ran.nextInt(6, 9);
        entities = new ArrayList<Entity>();
        mapPoints = Generators.genMap(size);
        booleanMap = new boolean[size + 1][size + 1];
    }

    public static boolean[][] initBoolArray(int size) {
        boolean[][] temp = new boolean[size][size];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[i][j] = true;
            }
        }

        return temp;
    }

    private static boolean[][] initBoolArray(int size, int ssize) {
        boolean[][] temp = new boolean[size][ssize];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < ssize; j++) {
                temp[i][j] = true;
            }
        }

        return temp;
    }

    private BMapPlusRooms randomRooms(boolean[][] bmap) {
        BMapPlusRooms returnStuff = new BMapPlusRooms(null, null);
        boolean[][] tempBmap = new boolean[bmap.length][bmap.length];
        ran.nextFloat();
        ran.nextBoolean();
        ran.nextInt();
        ArrayList<Room> tempList = new ArrayList<>();
        for (int i = 0; i < tempBmap.length; i++) {
            for (int j = 0; j < tempBmap.length; j++) {
                tempBmap[i][j] = true;
                if (!bmap[i][j]) {
                    tempBmap[i][j] = false;
                }
            }
        }
        Room playerRoom = new Room(tileSize, tileSize, 3, 3);
        tempList.add(playerRoom);
        addRoom(playerRoom, tempBmap);

        for (int i = 0; i < roomCount;) {
            int roomsize = ran.nextInt(3, 5);
            int x = ran.nextInt(3, mapSize - roomsize) * tileSize;
            int y = ran.nextInt(3, mapSize - roomsize) * tileSize;
            if (x / tileSize > 18) {
                System.err.println("x is wrong");
                System.exit(1);
            }
            if (y / tileSize > 18) {
                System.err.println("y is wrong");
                System.exit(1);
            }
            Room tempRoom = new Room(x, y, roomsize, roomsize);
            if (BooleanUtils.checkIfRoomFits(tempRoom, tempBmap)) {
                addRoom(tempRoom, tempBmap);
                tempList.add(tempRoom);
                i++;
            }
        }
        returnStuff = new BMapPlusRooms(tempList, tempBmap);
        return returnStuff;
    }

    private BMapPlusRooms bspRooms(boolean[][] bmap) {
        BMapPlusRooms returnStuff = new BMapPlusRooms(null, null);
        int bmapLength = bmap.length;
        boolean[][] tempBmap = initBoolArray(bmapLength);

        ArrayList<Room> tempList = null;
        tempList = new ArrayList<>();

        segments.clear();
        randomSplit(1, 1, bmapLength - 1, bmapLength - 1);
        for (Room segs : segments) {
            Double ranDob = ran.nextDouble();
            if (ranDob < 0.9) {
                if (ranDob > 0.5) {
                    tempList.add(new Room(segs.y(), segs.x(), segs.h(), segs.w()));
                } else {
                    if (segs.h() > 4 || segs.w() > 4) {
                        tempList.add(new Room(segs.y(), segs.x(), 4, 4));
                    } else {
                        tempList.add(new Room(segs.y(), segs.x(), 3, segs.w()));
                    }
                }
            }

        }
        assert tempList.size() <= segments.size() : "wow";
        for (Room segs : tempList) {
            tempBmap = addRoom(segs, tempBmap);
        }

        returnStuff = new BMapPlusRooms(tempList, tempBmap);

        return returnStuff;

    }

    private void randomSplit(int min_row, int min_col, int max_row, int max_col) {
        int seg_height = max_row - min_row;
        int seg_width = max_col - min_col;
        assert max_col < mapSize + 1 : "to high";
        assert max_row < mapSize + 1 : "to high";
        if (seg_height < MAX && seg_width < MAX) {
            segments.add(new Room(min_row, min_col, seg_height, seg_width));
        } else if (seg_height < MAX && seg_width >= MAX) {
            split_on_vertical(min_row, min_col, max_row, max_col);
        } else if (seg_height >= MAX && seg_width < MAX) {
            split_on_horizontal(min_row, min_col, max_row, max_col);
        } else {
            if (ran.nextDouble() < 0.5) {
                split_on_horizontal(min_row, min_col, max_row, max_col);
            } else {
                split_on_vertical(min_row, min_col, max_row, max_col);
            }
        }

    }

    private void split_on_vertical(int min_row, int min_col, int max_row, int max_col) {
        int split = Math.floorDiv((min_col + max_col), (2)) + ran.nextInt(1 + 1) - 1;
        randomSplit(min_row, min_col, max_row, split);
        randomSplit(min_row, split + 1, max_row, max_col);
    }

    private void split_on_horizontal(int min_row, int min_col, int max_row, int max_col) {
        int split = Math.floorDiv((min_row + max_row), (2)) + ran.nextInt(1 + 1) - 1;
        randomSplit(min_row, min_col, split, max_col);
        randomSplit(split + 1, min_col, max_row, max_col);
    }

    public void startMap() {
        for (Point p : mapPoints) {
            entities.add(new Entity(new WallEnt(new Vector2().x(p.x()).y(p.y())), YELLOW, tileSize));
            booleanMap[p.x() / tileSize][p.y() / tileSize] = true;
        }

        for (int i = 0; i < booleanMap.length; i++) {
            for (int j = 0; j < booleanMap.length; j++) {
                booleanMap[i][j] = true;
            }
        }
        BMapPlusRooms bMapPlusRooms = bspRooms(booleanMap);
        booleanMap = bMapPlusRooms.tempBMap();

        rooms = bMapPlusRooms.rooms();
        BooleanUtils.printBoolMap(booleanMap);

        boolean whileB = true;
        boolean[][] temp = new boolean[booleanMap.length][booleanMap.length];
        int count = 2000;

        while (whileB) {
            for (Room room : rooms) {
                room.connecetRooms.clear();
            }
            temp = Generators.placeTunnels2(rooms, booleanMap, ran);
            if (Generators.mapValidator(temp)) {
                booleanMap = temp;
                whileB = false;
                allRoomsConnected = true;
                System.out.println("valid");
            } else if (count <= 0) {
                booleanMap = temp;
                whileB = false;
                System.out.println("count low: " + count);
                for (Room room : rooms) {
                    System.out.println(room.connecetRooms);
                }
            }
            if (!allRoomsConnected) {
                Room room1 = null;
                for (Room room : rooms) {

                    if (room.connecetRooms.size() <= 1) {
                        System.out.println("yee");
                        room1 = room;
                    }
                    if (room1 != null && room1.connecetRooms.size() < 2) {
                        Room room2 = Generators.findClosest(room1, rooms, ran);
                        temp = Generators.placeTunnel(room1, room2, temp, ran);

                    }
                }

            }
            count--;
        }

        BooleanUtils.printBoolMap(booleanMap);

        // booleanMap = new Generators().flood(new Point(14, 14), booleanMap);
        for (Point p : mapPoints) {
            // System.err.println(p.x()+ " "+p.y());
            if (!booleanMap[(p.x()) / tileSize][(p.y()) / tileSize]) {
                entities.removeIf(a -> (int) a.entity().pos().x() == p.x() &&
                        (int) a.entity().pos().y() == p.y());
                if (unUsed == null) {
                    unUsed = p;
                }
            } else {
                // System.err.println(p.x());
                // System.exit(0);
            }
        }
    }

    public Random getRandom() {
        return ran;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity enti) {
        entities.add(enti);
    }

    public ArrayList<Point> getPoints() {
        return mapPoints;
    }

    public Point nextUnused() {
        return unUsed;
    }

    private static boolean[][] addRoom(Room room, boolean[][] bmap) {
        boolean[][] returnVal = setRoomToFalse(room.x(), room.y(), room.w(), room.h(), bmap);
        return returnVal;

    }

    private static boolean[][] setRoomToFalse(int x, int y, int w, int h, boolean[][] bMap) {

        for (int i = x; i < w + x; i++) {
            for (int j = y; j < h + y; j++) {
                bMap[i][j] = false;

            }

        }

        return bMap;
    }
    // 2
    // 1
    // 012

}
