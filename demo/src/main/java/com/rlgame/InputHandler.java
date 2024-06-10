package com.rlgame;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.IsKeyPressed;
import static com.raylib.Raylib.KEY_DOWN;
import static com.raylib.Raylib.KEY_H;
import static com.raylib.Raylib.KEY_J;
import static com.raylib.Raylib.KEY_K;
import static com.raylib.Raylib.KEY_L;
import static com.raylib.Raylib.KEY_LEFT;
import static com.raylib.Raylib.KEY_RIGHT;
import static com.raylib.Raylib.KEY_SPACE;
import static com.raylib.Raylib.KEY_UP;
import static com.rlgame.Globals.tileSize;

import com.raylib.Raylib.Vector2;
import com.rlgame.entities.PlayerEnt;

public class InputHandler {

    final static Float ofset = (float) tileSize;// +5;

    public static WeirdType inputManeger(Vector2 vec2, Game game) {
        Vector2 res = vec2;
        Runnable retRunnable = null;

        int keybordInput = keybord();

        switch (keybordInput) {
            case 264, 75: // down
                res = vec2.y(vec2.y() + ofset);
                break;
            case 265, 74: // up
                res = vec2.y(vec2.y() - ofset);
                break;
            case 263, 72: // left
                res = vec2.x(vec2.x() - ofset);
                break;
            case 262, 76: // right
                res = vec2.x(vec2.x() + ofset);
                break;
            case 32: // space
                retRunnable = () -> {

                    game.mapSetup.setGameMap(game.mapSetup.getCurrentIndex() + 1, 0);
                    game.mapOpen();
                };

            default:
                break;
        }

        WeirdType returnStuff = new WeirdType(res, retRunnable);

        return returnStuff;
    }

    private static int keybord() {
        int returnval = -1;
        if (IsKeyPressed(KEY_K)) { // 75
            returnval = KEY_K;
        }
        if (IsKeyPressed(KEY_DOWN)) { // 264
            returnval = KEY_DOWN;
        }
        if (IsKeyPressed(KEY_UP)) { // 265
            returnval = KEY_UP;
        }
        if (IsKeyPressed(KEY_J)) { // 74
            returnval = KEY_J;
        }
        if (IsKeyPressed(KEY_LEFT)) { // 263
            returnval = KEY_LEFT;
        }
        if (IsKeyPressed(KEY_H)) { // 72
            returnval = KEY_H;
        }
        if (IsKeyPressed(KEY_RIGHT)) { // 262
            returnval = KEY_RIGHT;
        }
        if (IsKeyPressed(KEY_L)) { // 76
            returnval = KEY_L;
        }
        if (IsKeyPressed(KEY_SPACE)) { // 32
            returnval = KEY_SPACE;
        }
        return returnval;
    }

}
