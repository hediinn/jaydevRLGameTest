package com.rlgame.entities;

import com.raylib.Raylib.Color;
import com.rlgame.EntityInterface;

public record Entity(EntityInterface entity, Color color, int size) {
    
}
