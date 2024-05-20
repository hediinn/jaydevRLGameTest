package com.rlgame;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
    }

    @Override
    public void interact(EntityInterface dmgTo) {
        dmgTo.interact(this);
    }
    @Override
    public String type() {
        return "Player";
    }
    
}
