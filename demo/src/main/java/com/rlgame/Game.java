package com.rlgame;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.ClearBackground;
import static com.raylib.Raylib.CloseWindow;
import static com.raylib.Raylib.DrawRectangle;
import static com.raylib.Raylib.EndDrawing;
import static com.raylib.Raylib.InitWindow;
import static com.raylib.Raylib.SetTargetFPS;
import static com.raylib.Raylib.Vector2Add;
import static com.raylib.Raylib.WindowShouldClose;

import com.raylib.Raylib.Vector2;

import static com.rlgame.Globals.tileSize;
import com.rlgame.Generators;

public class Game {


    List<Entity> entityList;
    ArrayList<Point> map;
    Point unUsedPoint;
    
    Vector2 playerPos;
    Vector2 enemyPos;
    PlayerEnt player;
    Entity playerEnt;

    EnemyEnt enemy;
    Entity enemyEnt;


    Vector2 movementVec = new Vector2().x(0).y(0);
    Entity playerTouches = null; 

    public void startGame(){
        GameMap map = new GameMap(10);
        map.startMap();
        entityList = map.getEntities();
        unUsedPoint = map.nextUnused();
        playerPos = new Vector2().x(unUsedPoint.x()+tileSize/4).y(unUsedPoint.y()+tileSize/4);
        
        player = new PlayerEnt(playerPos, 10);
        playerEnt = new Entity(player, GREEN, tileSize/2);

        //enemy = new EnemyEnt(enemyPos, 4);
        //enemyEnt = new Entity(enemy, BEIGE, tileSize/2);

//        entityList.add(enemyEnt);
        Stack<Entity> entsToRemove = new Stack<>();
        
        InitWindow(800, 800, "Demo");



        SetTargetFPS(60);
        while (!WindowShouldClose()) {




            //----------------------------------------------------------------------------------
            playerTouches = null;
            if(player.getHP()<= 0) {
                System.out.println("you lost");
                System.exit(0);
            }
            for (Entity entity : entityList) {
                if(entity.entity().type() == "Enemy") {
                    if(entity.entity().getHP() <=0) {
                        entsToRemove.add(entity);

                   }
                }
            }
            for (Entity entity : entsToRemove) {
                entityList.remove(entity);
            }
            entsToRemove.clear();
            move();
            for (Entity enemi : entityList) {
                if(checkMove(enemi) && enemi.entity().isBlocking()) { 
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

            for (Point point : map.getPoints()) {
                    DrawRectangle(point.x(), point.y(), tileSize, tileSize, GRAY);
         //           DrawText(""+point.x() + "\n"+point.y(), point.x(),point.y(), tileSize/4, BLUE);
            }
            for (Entity ent : entityList) {
                DrawRectangle((int)ent.entity().pos().x(), (int)ent.entity().pos().y(), ent.size(), ent.size(), ent.color());
            }
            DrawRectangle((int)playerEnt.entity().pos().x(), (int)playerEnt.entity().pos().y(), playerEnt.size(), playerEnt.size(), playerEnt.color());
            DrawText("?",(int)playerEnt.entity().pos().x(), (int)playerEnt.entity().pos().y(), playerEnt.size(), RED);

            EndDrawing();
        
        }
        playerPos.close();
        movementVec.close();
        for (Entity entity : entityList) {
            entity.entity().pos().close();
        }
        
        CloseWindow();
    }


    private boolean checkMove(Entity checkEnemy) {

        Vector2 ifMove = Vector2Add(movementVec, playerPos);
        Vector2 enemyVec = checkEnemy.entity().pos();
        
        if (enemyVec.x()+checkEnemy.size() >= ifMove.x()&&
            enemyVec.y()+checkEnemy.size() >= ifMove.y() &&
            enemyVec.x()<= ifMove.x() &&
            enemyVec.y()<= ifMove.y()
        ) 
        {
           return true; 
        }
        else
        {

            return false;
        }
    }
    private void move() {
        movementVec = movementVec.x(0).y(0);
        movementVec = Movement.move(movementVec);
    }

}
