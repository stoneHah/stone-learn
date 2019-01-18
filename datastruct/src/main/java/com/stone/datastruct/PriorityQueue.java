package com.stone.datastruct;

public class PriorityQueue {
    private Heap heap;

    public void insert(Heap.Node node) {
        heap.insert(node);
    }

    public Heap.Node remove(){
        return heap.remove();
    }
}
