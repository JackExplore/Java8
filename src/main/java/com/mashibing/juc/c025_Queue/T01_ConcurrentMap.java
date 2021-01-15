package com.mashibing.juc.c025_Queue;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * ConcurrentHashMap vs. ConcurrentSkipListMap
 * 区别是一个无序，一个排好序
 *
 * 未讲解，未执行
 */
public class T01_ConcurrentMap {

    public static void main(String[] args) {

        Map<String, String> map = new ConcurrentHashMap<>();

        Map<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();  // 高并发并且排序

        Random r = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ths.length);

        long start = System.currentTimeMillis();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    map.put("a" + r.nextInt(100000), "a" + r.nextInt(100000));
                    latch.countDown();
                }
            });
        }

        Arrays.asList(ths).forEach(t->t.start());

    }
}
