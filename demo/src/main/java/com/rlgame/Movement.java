package com.rlgame;

import static com.raylib.Jaylib.*;
import static com.rlgame.Globals.tileSize;

import com.raylib.Raylib.Vector2;

public class Movement {
    
    final static Float ofset = (float)tileSize;//+5;
    public static Vector2 move(Vector2 vec2) {
        Vector2 res = vec2;
        if (IsKeyPressed(KEY_DOWN)) {
            res = vec2.y(vec2.y()+ofset);
        }
        if (IsKeyPressed(KEY_UP)) {
            res = vec2.y(vec2.y()-ofset);
        }
        if (IsKeyPressed(KEY_LEFT)) {
            res = vec2.x(vec2.x()-ofset);
        }
        if (IsKeyPressed(KEY_RIGHT)) {
            res = vec2.x(vec2.x()+ofset);
        }
        return res;
    }
}
