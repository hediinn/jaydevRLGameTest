package com.rlgame.graphstuff;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.rlgame.Point;

public class Node{

    private Node left = null;
    private Node right = null;
    private Point data;

    public Node(Point p) {
        data = p;
    }
    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Point getData() {
        return data;
    }

    public void setData(Point data) {
        this.data = data;
    }



}
