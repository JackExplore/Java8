package com.mashibing.fiber;


public class HelloFiber {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                calc();
            }
        };

        for (int i = 0; i < 10000; i++) {

            // 1        688 ?
            Thread thread = new Thread(r);
            thread.start();

            // 2        15 ?
//            calc();

            // 3 ÓÐÎÊÌâ
//            Fiber<Void> fiber = new Fiber(new );

        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private static void calc() {
        int result = 0;
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 200; j++) {
                result += i;
            }
        }
    }
}
