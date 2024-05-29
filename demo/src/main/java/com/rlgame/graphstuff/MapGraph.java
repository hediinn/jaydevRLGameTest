package com.rlgame.graphstuff;

import java.util.Iterator;
import java.util.ArrayList;

public class MapGraph implements Iterable<Node> {

    ArrayList<Node> graph = new ArrayList<Node>();

    public void addNode(Node n) {
        graph.add(n);
    }
    @Override
    public Iterator<Node> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }
}
