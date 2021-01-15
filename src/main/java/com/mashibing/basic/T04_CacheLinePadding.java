package com.mashibing.basic;

public class T04_CacheLinePadding {

    public static volatile long[] arr = new long[16];     //

    public static void mainDifLine() throws Exception {

        Thread t1 = new Thread(() -> {
            for (long i = 0; i < 10_0000_0000L; i++) {
                arr[0] = i;
            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < 10_0000_0000L; i++) {
                arr[8] = i;
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long end = System.nanoTime();
        System.out.println("²»Í¬»º´æÐÐ(ms)£º" + (end - start) / 1000_000);
    }
}
