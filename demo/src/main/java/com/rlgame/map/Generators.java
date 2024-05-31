package com.rlgame.map;
import static com.rlgame.Globals.mapSize;
import static com.rlgame.Globals.tileSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.rlgame.BooleanUtils;
import com.rlgame.Point;

public class Generators {
    
    boolean awesomeSaws = true;
    private  boolean[][] flood(Point p,boolean[][] booleanMap, boolean state){
        boolean[][] bMap = booleanMap;
        if (state) {
            flood(p, bMap);
        }
        return bMap;
    }

    public static boolean[][] flood(Point p,boolean[][] booleanMap){ 
        boolean[][] bMap = booleanMap;
        int i = p.x(); 
        int j = p.y();

        
        if(i >= bMap.length ||j >= bMap.length){
            return bMap;
        }else if (i == -1 || j == -1) {
            return bMap;
            
        }else if(!bMap[i][j]){
            bMap[i][j] = true;
            bMap =flood(new Point(i+1,j), bMap);
            bMap =flood(new Point(i-1,j), bMap);
            bMap =flood(new Point(i,j-1), bMap);
            bMap =flood(new Point(i,j+1), bMap);
            
                
            return bMap;
        } else {
            return bMap;
        }

    }

    private boolean[][] placeTunnels2(ArrayList<Room> rooms,boolean[][] booleanMap, Random ran){
        if(awesomeSaws){
            return placeTunnels(rooms, booleanMap, ran);
        }else{
            return placeTunnels(rooms, booleanMap, ran);

        }

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
    public static boolean[][] placeTunnels(ArrayList<Room> rooms,boolean[][] booleanMap, Random ran){
        
        boolean[][] bMap = new boolean[booleanMap.length][booleanMap.length];
        for (int i = 0; i < bMap.length; i++) {
            for (int j = 0; j < bMap.length; j++) {
                bMap[i][j] = true;
                if(!booleanMap[i][j]){
                    bMap[i][j] = false;
                }
            }
        }
        
        boolean swap = false;
        List<Integer>potentialRooms = null;
        for (Room room : rooms) {
            room.connecetRooms.clear();
        }
        for (int room = 0; room <rooms.size()*2; room++) {
            Room room1 = null;
            Room room2 = null;
            int index = room%rooms.size();
            room1 = rooms.get(index);
            int x1 = ((room1.x()/tileSize) *2+ room1.w())/2;
            int y1 = ((room1.y()/tileSize) *2 +room1.h())/2;
            
            potentialRooms = rooms.stream().map(a ->
                            (int)(
                                Math.sqrt(
                                    Math.abs(
                                        Math.pow((((a.x()/(double)tileSize)*2.0+a.w())/2.0)-x1,2.0))+ 
                                    Math.abs(
                                        Math.pow((((a.y()/(double)tileSize)*2.0+a.h())/2.0)-y1,2.0)) 
                            ))
                        
                            )
            .collect(Collectors.toList());
            potentialRooms = potentialRooms.stream().filter(a ->a>0).toList();
            int index2 = potentialRooms.stream().min(Integer::compare).get();
            room2 = rooms.get(potentialRooms.indexOf(index2));
            boolean tag = true;
            int countMe = 0;
            while(tag == true){
                if(room1.getRooms().contains(room2) && room2.getRooms().contains(room1)){
                    for (Room r2 : rooms) {
                        if(
                            !r2.getRooms().contains(room1) && 
                            !room1.getRooms().contains(r2)
                            )
                            if(r2.getRooms().size() < 2){
                                room2 = r2;
                                tag = false;
                                break;
                            }
                        }
                    }else{
                        tag = false;
                    }
                    if(countMe>=5){
                        tag = false;

                    }
                    countMe++;
            }

            if(!room1.getRooms().contains(room2) || !room2.getRooms().contains(room1)){
                
                int x2 = (room2.x()/tileSize) *2+ room2.w();
                int y2 = (room2.y()/tileSize) *2+ room2.h();
                room1.addConnection(room2);
                room2.addConnection(room1);

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
                    swap = false;
                }
                if(swap) {
                    for(int i = wx1; i <=wx2;i++){
                        bMap[i][wy2] = false;
                    }
                    for(int j = wy1; j <=wy2;j++){
                        bMap[wx1][j] = false;
                    }
                    swap = false;
                }else {
                    for(int i = wx1; i <=wx2;i++){
                        bMap[i][wy1] = false;
                    }
                    for(int j = wy1; j <=wy2;j++){
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
                if(!mapToValidate[i][j]){
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

}
