package com.rlgame.entities;

import com.raylib.Raylib.Vector2;

public interface EntityInterface {

    public Vector2 pos();
    public int getHP();
    public void takeDamage(int dmg);
    public void interact(EntityInterface dmgTo);
    public String type();
    public boolean isBlocking();
    public void takeTurn();
    
}