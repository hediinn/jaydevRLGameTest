package com.rlgame;

import com.raylib.Raylib.Vector2;

public class EnemyEnt implements EntityInterface {


    private Vector2 pos;
    private int HP;

    public EnemyEnt(Vector2 startPos, int startHP) {
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
        HP -=dmg;
        System.out.println(HP);
        if(HP <= 0) {
            
        }
    }

    @Override
    public void interact(EntityInterface dmgTo) {
        if(dmgTo.type() == "Player") {
            takeDamage(2);
        }
    }


    @Override
    public String type() {
        return "Enemy";
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
