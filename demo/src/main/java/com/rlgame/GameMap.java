package com.rlgame;

import java.util.List;
import java.util.ArrayList;
import static com.rlgame.Globals.tileSize;

import com.raylib.Jaylib.Vector2;
import com.rlgame.Generators;
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
        room.add(new Room(ofset+tileSize*1,ofset+tileSize*1,3,3));
        room.add(new Room(ofset+tileSize*5,ofset+tileSize*4,5,2));
              
        
        for (Room roms : room) {
                for (Point p : map) {
                
                    if( 
                        roms.x() <= p.x() && 
                        roms.y() <= p.y() &&
                        roms.x()+ tileSize*roms.w() > p.x()&&
                        roms.y()+ tileSize*roms.h() > p.y()
                        ) {
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
