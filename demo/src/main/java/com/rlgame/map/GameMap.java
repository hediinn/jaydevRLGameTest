package com.rlgame.map;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.ArrayList;

import static com.rlgame.Globals.mapSize;
import static com.rlgame.Globals.tileSize;

import com.raylib.Jaylib.Vector2;
import com.rlgame.BooleanUtils;
import com.rlgame.Entity;
import com.rlgame.Point;
import com.rlgame.WallEnt;

import static com.raylib.Jaylib.*;

public class GameMap {
    
    private ArrayList<Point> map;
    private boolean[][] booleanMap;
    private List<Entity> entities;
    private Point unUsed = null;
    private int seed = 2;

    
    public GameMap(int size) {
        entities = new ArrayList<Entity>();
        map = Generators.genMap(size);
        booleanMap = new boolean[size+1][size+1];
    }
    public void startMap() {
        for (Point p : map) {
            entities.add(new Entity(new WallEnt(new Vector2().x(p.x()).y(p.y())), YELLOW, tileSize));
            booleanMap[p.x()/tileSize][p.y()/tileSize] = true;
        }

        
        ArrayList<Room> rooms = new ArrayList<>();


        Random ran = new Random(2);
        
        for (int i = 0; i < booleanMap.length; i++) {
            for (int j = 0; j < booleanMap.length; j++) {
                booleanMap[i][j] = true;
            }
        }
        Room playerRoom = new Room(tileSize,tileSize,2,2);
        rooms.add(playerRoom);
        addRoom(playerRoom);
        for (int i = 0; i < 8;) {
            int roomsize = ran.nextInt(2,4);
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
            if(BooleanUtils.checkIfRoomFits(tempRoom,booleanMap))
                {
                    rooms.add(tempRoom);
                    addRoom(tempRoom);
                    i++;
                }  
        }
        BooleanUtils.printBoolMap(booleanMap);
        placeTunnels(rooms);

        
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
        System.out.println(room);
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
    private void placeTunnels(ArrayList<Room> rooms){
     
        boolean swap = true;
        for (int room = 0; room <rooms.size(); room++) {
            int x1 = ((rooms.get(room).x()/tileSize) *2+ rooms.get(room).w())/2;
            int y1 = ((rooms.get(room).y()/tileSize) *2 +rooms.get(room).h())/2;
            
            List<Integer>potentialRooms = rooms.stream().map(a ->
                            (int)(
                                Math.sqrt(
                                    Math.abs(
                                        Math.pow((((a.x()/(double)tileSize)*2.0+a.w())/2.0)-x1,2.0))+ 
                                    Math.abs(
                                        Math.pow((((a.y()/(double)tileSize)*2.0+a.h())/2.0)-y1,2.0)) 
                            ))
                        
                            )
            .collect(Collectors.toList());

            int index2 = potentialRooms.stream().filter(a -> a>0).min((i,j) -> i.compareTo(j)).get();
            
            int x2 = (rooms.get(potentialRooms.indexOf(index2)).x()/tileSize) *2+ rooms.get(potentialRooms.indexOf(index2)).w();
            int y2 = (rooms.get(potentialRooms.indexOf(index2)).y()/tileSize) *2+ rooms.get(potentialRooms.indexOf(index2)).h();
            x2 = x2/2;
            y2 = y2/2;


            
            

            int wx1,wx2,wy1,wy2;
            wx1 = x1;
            wx2 = x2;
            wy1 = y1;
            wy2 = y2;
            if (x2 < x1 || y2< y1) {
                wx1 = x2;
                wx2 = x1;
                wy1 = y2;
                wy2 = y1;
            }
            if(swap) {
                for(int i = wx1; i <=wx2;i++){
                    booleanMap[i][wy2] = false;
                }
                for(int j = wy1; j <=wy2;j++){
                    booleanMap[wx1][j] = false;
                }
                swap = false;
            }else {
                for(int i = wx1; i <=wx2;i++){
                    booleanMap[i][wy1] = false;
                }
                for(int j = wy1; j <=wy2;j++){
                    booleanMap[wx2][j] = false;
                }
                swap = true;
 
 
            }





        }
    }
}
