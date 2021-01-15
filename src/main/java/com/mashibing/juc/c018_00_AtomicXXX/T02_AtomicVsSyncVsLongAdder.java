package com.mashibing.juc.c018_00_AtomicXXX;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class T02_AtomicVsSyncVsLongAdder {

    static long count2 = 0L;

    static AtomicLong count1 = new AtomicLong(0L);

    /**
     * LongAdder则是内部维护一个Cells数组，每个Cell里面有一个初始值为0的long型变量，
     * 在同等并发量的情况下，争夺单个变量的线程会减少，这是变相的减少了争夺共享资源的并发量，
     * 另外多个线程在争夺同一个原子变量时候，如果失败并不是自旋CAS重试，而是尝试获取其他原子变量的锁，
     * 最后当获取当前值时候是把所有变量的值累加后再加上base的值返回的。
     */
    static LongAdder count3 = new LongAdder();

    public static void main(String[] args) throws InterruptedException {
        int plusCount = 100000;
        Thread[] threads = new Thread[1000];
        long startAdder = 0L, endAdder = 0L;
        long startAtomic = 0L, endAtomic = 0L;
        long startNormal = 0L, endNormal = 0L;

        // Type1: LongAdder
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < plusCount; k++) {
                    count3.increment();
                }
            });
        }
        startAdder = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        endAdder = System.currentTimeMillis();
        System.out.println("LongAdder cost : " + (endAdder - startAdder));

        // Type2: AtomicLong
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < plusCount; k++) {
                    count1.incrementAndGet();
                }
            });
        }
        startAtomic = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        endAtomic = System.currentTimeMillis();
        System.out.println("AtomicLong cost : " + (endAtomic - startAtomic));

        // Type3: Long ++
        final Object o = new Object();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < plusCount; k++) {
                    synchronized (o){
                        count2++;
                    }
                }
            });
        }
        startNormal = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        endNormal = System.currentTimeMillis();
        System.out.println("Sync Long++ cost : " + (endNormal - startNormal));

        // result
        System.out.println("\nThe result correct:");
        System.out.println("AtomicLong : " + count1);
        System.out.println("LongAdder : " + count3);
        System.out.println("Sync Long++ : " + count2);

    }
}
