package com.rlgame;

public class Point {

    private int _x;
    private int _y;
    private boolean inUse;
    public Point(int x, int y, boolean used) {
        _x = x;
        _y = y;
        inUse = used;
    }

        
    public void setUsed(boolean set) {
        inUse = set;
    }
    public boolean used(){
        return inUse;
    }
    public int x() {
        return _x;
    }
    public int y() {
        return _y;
    }
        
}
    
