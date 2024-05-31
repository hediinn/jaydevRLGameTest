package com.rlgame.map;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import static com.rlgame.Globals.mapSize;
import static com.rlgame.Globals.seed;
import static com.rlgame.Globals.tileSize;

import com.raylib.Jaylib.Vector2;
import com.rlgame.BooleanUtils;
import com.rlgame.Point;
import com.rlgame.entities.Entity;
import com.rlgame.entities.WallEnt;

import static com.raylib.Jaylib.*;

public class GameMap {
    
    private ArrayList<Point> map;
    private boolean[][] booleanMap;
    private List<Entity> entities;
    private Point unUsed = null;
    private int roomCount = 8;
    //private int seed = 0; green seed
    private Random ran = new Random(seed);
       
    private ArrayList<Room> rooms = new ArrayList<>();
    public GameMap(int size) {
        entities = new ArrayList<Entity>();
        map = Generators.genMap(size);
        booleanMap = new boolean[size+1][size+1];
    }

    private ArrayList<Room> randomRooms(boolean[][] bmap) {
        ArrayList<Room> tempList = new ArrayList<>();
        Room playerRoom = new Room(tileSize,tileSize,3,3);
        tempList.add(playerRoom);
        addRoom(playerRoom);


        for (int i = 0; i < roomCount;) {
            int roomsize = ran.nextInt(3,5);
            int x = ran.nextInt(3,mapSize-roomsize) * tileSize;
            int y = ran.nextInt(3,mapSize-roomsize) * tileSize;
            if(x/tileSize >18) {
                System.err.println("x is wrong");
                System.exit(1);
            }
            if(y/tileSize >18) {
                System.err.println("y is wrong");
                System.exit(1);
            }
            Room tempRoom = new Room(x,y,roomsize,roomsize);
            if(BooleanUtils.checkIfRoomFits(tempRoom,bmap))
                {
                    addRoom(tempRoom);
                    tempList.add(tempRoom);
                    i++;
                }  
        }
        return tempList;
    }




    public void startMap() {
        for (Point p : map) {
            entities.add(new Entity(new WallEnt(new Vector2().x(p.x()).y(p.y())), YELLOW, tileSize));
            booleanMap[p.x()/tileSize][p.y()/tileSize] = true;
        }



        
        for (int i = 0; i < booleanMap.length; i++) {
            for (int j = 0; j < booleanMap.length; j++) {
                booleanMap[i][j] = true;
            }
        }
        rooms = randomRooms(booleanMap);
        

        boolean whileB = true;  
        boolean[][] temp = new boolean[booleanMap.length][booleanMap.length];
        int count = 100;

        while (whileB) {
            
            temp = Generators.placeTunnels(rooms,booleanMap, ran);    
            if(Generators.mapValidator(temp)){
                booleanMap = temp;
                whileB = false;
                System.out.println("valid");
            }else if(count<=0) {
                booleanMap = temp;
                whileB = false;
                System.out.println("count low: "+count);
            }
            else{
                
            }
            count--;
        }

        BooleanUtils.printBoolMap(booleanMap);
      //  booleanMap = new Generators().flood(new Point(14, 14), booleanMap);
        for (Point p : map) {
            //System.err.println(p.x()+ "  "+p.y());
            if( 
                !booleanMap[(p.x()/tileSize)][(p.y()/tileSize)]
                ) 
                {
                    entities.removeIf(a -> 
                                            (int)a.entity().pos().x() == p.x() &&
                                            (int)a.entity().pos().y() == p.y());
                    if(unUsed == null) {
                        unUsed = p;
                    }
            }else {
            // System.err.println(p.x());
            //  System.exit(0);
            }            
        }
    }
    public Random getRandom(){
        return ran;
    }
            
    public ArrayList<Room> getRooms(){
        return rooms;
    }

    public List<Entity> getEntities() {
        return entities;
    }
    public void addEntity(Entity enti) {
        entities.add(enti);
    }
    public ArrayList<Point> getPoints() {
        return map;
    }

    public Point nextUnused() {
        return unUsed;
    }
    private void addRoom(Room room) {
        System.out.println(room.x() + "  " + room.y());
        setRoomToFalse(room.x()/tileSize, room.y()/tileSize,room.w(),room.h());
    }

    private void setRoomToFalse(int x, int y, int w, int h) {
        for (int row = 0; row < w; row++) {
            for(int col = 0; col<h;col++) {
                int _x = x + row;
                int _y = y + col;
                booleanMap[_x][_y] = false;


            }
            
        }
    
    }

    //2
    //1
    //012
    
}
