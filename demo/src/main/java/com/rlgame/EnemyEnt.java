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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHP'");
    }

    @Override
    public void takeDamage(int dmg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
    }

    @Override
    public void dealDamage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dealDamage'");
    }
    
}
