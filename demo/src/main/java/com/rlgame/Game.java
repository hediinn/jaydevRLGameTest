package com.rlgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.ClearBackground;
import static com.raylib.Raylib.CloseWindow;
import static com.raylib.Raylib.DrawRectangle;
import static com.raylib.Raylib.DrawText;
import static com.raylib.Raylib.EndDrawing;
import static com.raylib.Raylib.InitWindow;
import static com.raylib.Raylib.SetTargetFPS;
import static com.raylib.Raylib.Vector2Add;
import static com.raylib.Raylib.WindowShouldClose;

import com.raylib.Raylib.Vector2;

import static com.rlgame.Globals.mapSize;
import static com.rlgame.Globals.seed;
import static com.rlgame.Globals.tileSize;

import com.rlgame.entities.EnemyEnt;
import com.rlgame.entities.Entity;
import com.rlgame.entities.PlayerEnt;
import com.rlgame.entities.PotionEnt;
import com.rlgame.map.GameMap;
import com.rlgame.map.MapSetup;
import com.rlgame.map.Room;

public class Game {

    List<Entity> entityList;
    List<Entity> noneWallEntityList;
    ArrayList<Point> mapPoint;
    Point unUsedPoint;

    Vector2 playerPos;
    Vector2 enemyPos;
    PlayerEnt player;
    Entity playerEnt;

    EnemyEnt enemy;
    Entity enemyEnt;
    Random random = new Random(seed);

    Vector2 movementVec = new Vector2().x(0).y(0);
    Entity playerTouches = null;
    MapSetup mapSetup = null;
    GameMap map = null;

    public void mapOpen() {
        entityList = null;
        map = null;
        unUsedPoint = null;
        playerPos = null;
        noneWallEntityList = null;
        map = mapSetup.getCurrentMap();
        entityList = map.getEntities();
        noneWallEntityList = new ArrayList<>();

        unUsedPoint = map.nextUnused();
        playerPos = new Vector2().x(unUsedPoint.x() + tileSize / 4).y(unUsedPoint.y() + tileSize / 4);

        player = new PlayerEnt(playerPos, 10);
        playerEnt = new Entity(player, GREEN, tileSize / 2);
        int enemyCount = 5;
        int potionCount = 2;
        for (Room room : map.getRooms()) {
            int random = map.getRandom().nextInt(0, 4);
            int x = room.x() * tileSize;
            int y = room.y() * tileSize;
            Vector2 enemyVec = new Vector2().x(x + tileSize / 4).y(y + tileSize / 4);
            if (!(enemyVec.x() == playerPos.x() && enemyVec.y() == playerPos.y()) && random < 3 && enemyCount > 0) {
                noneWallEntityList.add(new Entity(new EnemyEnt(enemyVec, 10 - random), RED, tileSize / 2));
                enemyCount--;
            }
            if (!(enemyVec.x() == playerPos.x() && enemyVec.y() == playerPos.y()) && (random >= 3 && potionCount > 0)) {
                noneWallEntityList.add(new Entity(new PotionEnt(enemyVec), GREEN, tileSize / 2));
                potionCount--;
            }
        }

    }

    public void startGame() {

        mapSetup = new MapSetup(3, random);

        mapOpen();

        Stack<Entity> entsToRemove = new Stack<>();

        // START OF GAME
        InitWindow(800, 800, "Demo");
        SetTargetFPS(60);
        while (!WindowShouldClose()) {

            // ----------------------------------------------------------------------------------
            playerTouches = null;
            if (player.getHP() <= 0) {
                System.out.println("you lost");
                System.exit(0);
            }
            for (Entity entity : noneWallEntityList) {
                if (entity.entity().type() == "Enemy") {
                    if (entity.entity().getHP() <= 0) {
                        entsToRemove.add(entity);
                    }
                }
                if (entity.entity().type() == "Potion") {
                    if (entity.entity().getHP() <= 0) {
                        entsToRemove.add(entity);
                    }
                }
            }
            for (Entity entity : entsToRemove) {
                noneWallEntityList.remove(entity);
            }
            entsToRemove.clear();
            move();
            for (Entity enemi : noneWallEntityList) {
                if (checkMove(enemi) && enemi.entity().isBlocking()) {
                    movementVec.x(0).y(0);
                    playerTouches = enemi;
                }
            }

            if (playerTouches != null) {
                player.interact(playerTouches.entity());
            }

            player.pos().x(movementVec.x() + player.pos().x()).y(movementVec.y() + player.pos().y());

            // Draw
            // ----------------------------------------------------------------------------------
            BeginDrawing();

            ClearBackground(RAYWHITE);

            for (Point point : map.getPoints()) {
                DrawRectangle(point.x(), point.y(), tileSize, tileSize, GRAY);
                // DrawText(""+point.x() + "\n"+point.y(), point.x(),point.y(), tileSize/4,
                // BLUE);
            }
            for (Entity ent : noneWallEntityList) {
                DrawRectangle((int) ent.entity().pos().x(), (int) ent.entity().pos().y(), ent.size() - 2,
                        ent.size() - 2, ent.color());
            }

            for (Entity ent : entityList) {
                DrawRectangle((int) ent.entity().pos().x(), (int) ent.entity().pos().y(), ent.size() - 2,
                        ent.size() - 2, ent.color());
            }
            DrawRectangle((int) playerEnt.entity().pos().x(), (int) playerEnt.entity().pos().y(), playerEnt.size(),
                    playerEnt.size(), playerEnt.color());
            DrawText("?", (int) playerEnt.entity().pos().x() + 3, (int) playerEnt.entity().pos().y() + 3,
                    playerEnt.size(), RED);
            DrawText(player.getHP() + "", 100, 745, 25, RED);

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

        if (enemyVec.x() + checkEnemy.size() >= ifMove.x() &&
                enemyVec.y() + checkEnemy.size() >= ifMove.y() &&
                enemyVec.x() <= ifMove.x() &&
                enemyVec.y() <= ifMove.y()) {
            return true;
        } else {

            return false;
        }
    }

    private void move() {
        movementVec = movementVec.x(0).y(0);
        WeirdType returnStuff = InputHandler.inputManeger(movementVec, this);
        movementVec = returnStuff.vec2();
        if (returnStuff.func() != null) {
            returnStuff.func().run();
        }
    }

    public void healPlayer() {
        player.takeDamage(3);
    }
}
