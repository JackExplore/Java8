package com.mashibing.juc.c025_Queue;

import java.util.PriorityQueue;

/**
 * 内部实现了排序
 *
 * 二叉树 堆排序
 */
public class T07_01_PriorityQueue {

    public static void main(String[] args) {

        PriorityQueue<String> q = new PriorityQueue<>();

        q.add("c");
        q.add("e");
        q.add("a");
        q.add("d");
        q.add("z");

        for (int i = 0; i < 5; i++) {
            System.out.println(q.poll());
        }
    }
}
