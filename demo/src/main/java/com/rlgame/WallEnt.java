package com.rlgame;

import com.raylib.Raylib.Vector2;

public class WallEnt implements EntityInterface{

    private Vector2 pos;
    public WallEnt (Vector2 startPos) {
        pos = startPos;
    }

    @Override
    public Vector2 pos() {
        return pos;
    }

    @Override
    public int getHP() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHP'");
    }

    @Override
    public void takeDamage(int dmg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
    }

    @Override
    public void interact(EntityInterface dmgTo) {
        if(dmgTo.type() == "Player") {
            System.out.println("This is a wall");
        }
    }

    @Override
    public String type() {
        return "Wall";
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public void takeTurn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeTurn'");
    }
    
}
