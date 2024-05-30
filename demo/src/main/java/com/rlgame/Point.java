package com.rlgame;

public class Point {

    private int _x;
    private int _y;
    public Point(int x, int y ) {
        _x = x;
        _y = y;
    }

    public int x() {
        return _x;
    }
    public int y() {
        return _y;
    }
    public boolean isTheSame(Point p) {
        return _x == p.x() && _y == p.y();
    }
        
    @Override
    public String toString() {
        return "x: "+_x +"  y: "+_y;
    }
}
    
