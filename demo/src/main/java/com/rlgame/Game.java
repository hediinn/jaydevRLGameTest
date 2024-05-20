package com.rlgame;
import java.util.ArrayList;
import java.util.List;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.ClearBackground;
import static com.raylib.Raylib.CloseWindow;
import static com.raylib.Raylib.DrawRectangle;
import static com.raylib.Raylib.EndDrawing;
import static com.raylib.Raylib.InitWindow;
import static com.raylib.Raylib.SetTargetFPS;
import static com.raylib.Raylib.WindowShouldClose;

import com.raylib.Raylib.Vector2;


public class Game {
    ArrayList<Point> map = genMap();
    Vector2 playerPos = new Vector2().x(map.get(0).x()+25).y(map.get(0).y()+25);
    Vector2 enemyPos = new Vector2().x(map.get(2).x() + 25.0f).y(map.get(2).y()+ 25.0f);
    List<Entity> entityList = new ArrayList<Entity>();
    public void startGame(){
        entityList.add(new Entity(playerPos,GREEN, true));
        entityList.add(new Entity(enemyPos, BEIGE,false));
        InitWindow(800, 800, "Demo");



        SetTargetFPS(60);
        while (!WindowShouldClose()) {
        //----------------------------------------------------------------------------------
            playerPos = Movement.move(playerPos);
            
        // Draw
        //----------------------------------------------------------------------------------
            BeginDrawing();

            ClearBackground(RAYWHITE);

            for (Point point : map) {
                    DrawRectangle(point.x(), point.y(), 100, 100, GRAY);
            }
            for (Entity ent : entityList) {
                DrawRectangle((int)ent.pos().x(), (int)ent.pos().y(), 50, 50, ent.color());
            }
            EndDrawing();
        
        }
        playerPos.close();
        enemyPos.close();
        CloseWindow();
    }

private static ArrayList<Point> genMap() {
    ArrayList<Point> map = new ArrayList<>();
    for (int row = 0; row < 5; row++) {
        for (int col = 0; col < 5; col++) {
            Point point = new Point(30+row*105, 30+col*105);
            map.add(point);
        }
    }
    return map;
}
}
