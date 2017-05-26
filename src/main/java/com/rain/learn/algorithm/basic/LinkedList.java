package com.rain.learn.algorithm.basic;

class Node {
    Node next;
}

public class LinkedList {
    private Node head;

    public LinkedList(Node head) {
        this.head = head;
    }

    public boolean existCircle() {
        Node step1 = head;
        Node step2 = head;
        while (step2 != null && step2.next != null) {
            step1 = step1.next;
            step2 = step2.next.next;
            if (step1 == step2) {
                return true;
            }
        }
        return false;
    }
}
