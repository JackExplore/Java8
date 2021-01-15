package com.mashibing.juc.c025_Queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class T04_ConcurrentQueue {

    public static void main(String[] args) {

        Queue<String> strs = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < 10; i++) {
            strs.offer("a" + i);    // add  /   offer 会给一个返回值，是否添加成功
        }

        System.out.println(strs);

        System.out.println(strs.size());
        System.out.println(strs.poll());    // 去取，而且 remove

        System.out.println(strs.peek());    // 去取，但并不会 remove
        System.out.println(strs.size());

        // 双端队列 Deque
    }
}
