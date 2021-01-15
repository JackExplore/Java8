package com.mashibing.basic;

public class T03_CacheLinePadding {

    public static volatile long[] arr = new long[2];     // 8 byte

    public static void mainSameLine() throws Exception {

        Thread t1 = new Thread(() -> {
            for (long i = 0; i < 10_0000_0000L; i++) {
                arr[0] = i;
            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < 10_0000_0000L; i++) {
                arr[0] = i;
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long end = System.nanoTime();
        System.out.println("ÏàÍ¬»º´æÐÐ(ms)£º" + (end - start) / 1000_000);
        /**
         * 1s = 1000ms          ºÁÃë
         * 1s = 1000_000us      Î¢Ãë
         * 1s = 1000_000_000ns  ÄÉÃë
         */
    }
}
