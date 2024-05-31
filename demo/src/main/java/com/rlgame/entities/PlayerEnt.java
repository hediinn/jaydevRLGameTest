package com.rlgame.entities;

import com.raylib.Raylib.Vector2;

public class PlayerEnt implements EntityInterface {

    private Vector2 pos;
    private int HP;

    public PlayerEnt(Vector2 startPos, int startHP) {
        pos = startPos;
        HP = startHP;

    }
    @Override
    public Vector2 pos() {
        return pos;
    }

    @Override
    public int getHP() {
        return HP;
    }

    @Override
    public void takeDamage(int dmg) {
        HP = HP +dmg;
    }

    @Override
    public void interact(EntityInterface dmgTo) {
        dmgTo.interact(this);
    }
    @Override
    public String type() {
        return "Player";
    }
    @Override
    public boolean isBlocking() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isBlocking'");
    }
    @Override
    public void takeTurn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeTurn'");
    }
    
}
