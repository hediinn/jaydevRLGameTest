package com.rlgame.graphstuff;
import java.util.ArrayList;

import org.bytedeco.javacpp.annotation.ArrayAllocator;

import com.rlgame.Point;
public class Node {

    private ArrayList<Node> nabers; 
    private Point point;  
    public Node(Point p,ArrayList<Node> nabo){
        nabers = nabo;
        point = p;

    }
}
