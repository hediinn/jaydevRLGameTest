package com.rlgame;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import static com.rlgame.Globals.tileSize;

import com.raylib.Jaylib.Vector2;
import static com.raylib.Jaylib.*;

public class GameMap {
    
    private ArrayList<Point> map;
    private List<Entity> entities;
    private Point unUsed = null;
    private int ofset = 32;
    public GameMap(int size) {
        entities = new ArrayList<Entity>();
        map = Generators.genMap(size);
    }
    public void startMap() {
        for (Point p : map) {
            entities.add(new Entity(new WallEnt(new Vector2().x(p.x()).y(p.y())), YELLOW, tileSize));
            p.setUsed(true);
        }

        
        ArrayList<Room> room = new ArrayList<>();
        Random ran = new Random();
        
        room.add(new Room(ofset+tileSize*1,ofset+tileSize*1,2,2));
        
        for (int i = 0; i < 1; i++) {
            int x = ran.nextInt(3,8);
            int y = ran.nextInt(3,8);
            if(!(
                room.get(i).x() >= x &&
                room.get(i).y() >= y &&
                room.get(i).x() +tileSize*room.get(i).w() < x &&
                room.get(i).y() +tileSize*room.get(i).h() < y 
                ))
                {
                    room.add(new Room(ofset+tileSize*x,ofset+tileSize*y,4,4));
                }  
        }
        
        for (Room roms : room) {
                for (Point p : map) {
                
                    if( 
                        roms.x() <= p.x() && 
                        roms.y() <= p.y() &&
                        roms.x()+ tileSize*roms.w() > p.x()&&
                        roms.y()+ tileSize*roms.h() > p.y()
                        ) 
                        {
                            p.setUsed(false);
                    
                            System.out.println(""+p.x());
                            entities.removeIf(a -> (int)a.entity().pos().x() == p.x() && (int)a.entity().pos().y() == p.y());
                            if(unUsed == null) {
                                unUsed = p;
                            }
                    }else {
                    // System.err.println(p.x());
                    //  System.exit(0);
                    }            
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


}
