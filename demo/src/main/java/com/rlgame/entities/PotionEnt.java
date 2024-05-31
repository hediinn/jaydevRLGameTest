package com.rlgame.entities;

import com.raylib.Raylib.Vector2;

public class PotionEnt implements EntityInterface {

    private Vector2 point;
    private String type = "Potion";
    private int HP = 1;


    public PotionEnt(Vector2 p) {
        point = p;
    }

    @Override
    public Vector2 pos() {
        return point;
    }

    @Override
    public int getHP() {
        return HP;
    }

    @Override
    public void takeDamage(int dmg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
    }

    @Override
    public void interact(EntityInterface dmgTo) {
        dmgTo.takeDamage(2);
        HP = 0;
    }

    @Override
    public String type() {
        return type;
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
