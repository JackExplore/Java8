package com.mashibing.juc.c025_Queue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Queue 和 List 的区别在哪里？
 * Queue 提供了很多对线程友好的 API
 */
public class T06_ArrayBlockingQueue {

    static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);

    static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            strs.put("a" + i);
        }
//        System.out.println(strs);
//        strs.take();
//        strs.put("aaa");  // 会阻塞
        strs.offer("aaa");  // 会以返回值判断是否成功
        strs.offer("aaa", 1, TimeUnit.SECONDS); // 等待时长
//        strs.add("bbb");    // 直接报异常

        System.out.println(strs);
    }
}
