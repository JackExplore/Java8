package com.mashibing.basic;

import sun.misc.Contended;

public class ContendedCacheLine {

    @Contended
    volatile long x;
    @Contended
    volatile long y;

    public static void main(String[] args) throws Exception{

        ContendedCacheLine t = new ContendedCacheLine();
        long times = 10000_0000L;

        Thread t1 = new Thread(() -> {
            for (long i = 0; i < times; i++) {
                t.x = i;
            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < times; i++) {
                t.y = i;
            }
        });

        long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long end = System.nanoTime();
        System.out.println("ºÄÊ±(ms)£º" + (end - start) / 1000_000);
    }
}
