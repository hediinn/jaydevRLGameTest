package com.rlgame.map;

import java.util.ArrayList;

public class Room{
    int x,y,w,h;
    



    public Room(int x,int y, int w,int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    
    public ArrayList<Room> connecetRooms= new ArrayList<>();
    
    public void addConnection(Room r) {
        if(r != this && !connecetRooms.contains(r)) {
            connecetRooms.add(r);
        }
    }
    public ArrayList<Room> getRooms(){
        return connecetRooms;
    }
    public int x() {
        return x;
    }

    public void x(int x) {
        this.x = x;
    }

    public int y() {
        return y;
    }

    public void y(int y) {
        this.y = y;
    }

    public int w() {
        return w;
    }
    public void w(int w) {
        this.w = w;
    }

    public int h() {
        return h;
    }

    public void h(int h) {
        this.h = h;
    }
}
