package com.mashibing.juc.c023_02_FromHashtableToCHM;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 插入的效率低一些，只是说读的效率更高一些，读，读，读，的效率
 */
public class T04_TestConcurrentHashMap {

    static ConcurrentHashMap<UUID, UUID> m = new ConcurrentHashMap<>();

    static int count = Constants.COUNT;
    static UUID[] keys = new UUID[count];
    static UUID[] values = new UUID[count];

    static final int THREAD_COUNT = Constants.THREAD_COUNT;

    static {
        for (int i = 0; i < count; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static class MyThread extends Thread {
        int start;
        int gap = count / THREAD_COUNT;

        public MyThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < start + gap; i++) {
                m.put(keys[i], values[i]);
            }
        }
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread(i * (count / THREAD_COUNT));
        }
        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("--------- put()-------------");
        System.out.println("ConcurrentHashMap / Time cost : " + (end - start));
        System.out.println("ConcurrentHashMap / Size : " + m.size());

        //-----------------------------------------------------

        start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000000; j++) {    // 一千万次
                    m.get(keys[10]);
                }
            });
        }
        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = System.currentTimeMillis();
        System.out.println("--------- get()-------------");
        System.out.println("SynchronizedHashMap / Time cost : " + (end - start));
    }
    /** out:
     *
     * --------- put()-------------
     * ConcurrentHashMap / Time cost : 518
     * ConcurrentHashMap / Size : 1000000
     * --------- get()-------------
     * SynchronizedHashMap / Time cost : 792
     *
     */
}
