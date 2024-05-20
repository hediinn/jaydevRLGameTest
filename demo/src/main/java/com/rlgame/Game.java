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

    PlayerEnt player = new PlayerEnt(playerPos, 10);
    Entity playerEnt = new Entity(player,GREEN, true);


    EnemyEnt enemy = new EnemyEnt(enemyPos, 4);
    Entity enemyEnt = new Entity(enemy, BEIGE, false);


    List<Entity> entityList = new ArrayList<Entity>();
    Vector2 movementVec = new Vector2().x(0).y(0);
    Entity playerTouches = null; 

    public void startGame(){
        entityList.add(enemyEnt);
        InitWindow(800, 800, "Demo");



        SetTargetFPS(60);
        while (!WindowShouldClose()) {
            //----------------------------------------------------------------------------------
            playerTouches = null;
            move();
            for (Entity enemi : entityList) {
                if(checkMove(enemi.entity())) { 
                    movementVec.x(0).y(0);
                    playerTouches = enemi;
                }
            }


            if (playerTouches != null) {
                player.interact(playerTouches.entity());
            }



            player.pos().x(movementVec.x()+player.pos().x()).y(movementVec.y()+player.pos().y());
            
            // Draw
            //----------------------------------------------------------------------------------
            BeginDrawing();

            ClearBackground(RAYWHITE);

            for (Point point : map) {
                    DrawRectangle(point.x(), point.y(), 100, 100, GRAY);
            }
            for (Entity ent : entityList) {
                DrawRectangle((int)ent.entity().pos().x(), (int)ent.entity().pos().y(), 50, 50, ent.color());
            }
            DrawRectangle((int)playerEnt.entity().pos().x(), (int)playerEnt.entity().pos().y(), 50, 50, playerEnt.color());
            EndDrawing();
        
        }
        playerPos.close();
        enemyPos.close();
        movementVec.close();
        
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
    private boolean checkMove(EntityInterface checkEnemy) {

        
        if(movementVec.x()+player.pos().x() == checkEnemy.pos().x() && movementVec.y()+player.pos().y() == checkEnemy.pos().y()) { 
            return true;
        }

        else{

            return false;
        }
    }
    private void move() {
        movementVec = movementVec.x(0).y(0);
        movementVec = Movement.move(movementVec);
    }

}
