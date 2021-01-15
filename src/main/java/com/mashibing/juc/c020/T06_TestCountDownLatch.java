package com.mashibing.juc.c020;

import java.util.concurrent.CountDownLatch;

public class T06_TestCountDownLatch {

    public static void main(String[] args) {
        usingCountDownLatch();
        usingJoin();
    }

    private static void usingCountDownLatch(){
        Thread[] threads = new Thread[100];

        /**
         * !
         */
        CountDownLatch latch = new CountDownLatch(threads.length);

        System.out.println("start latch ...");

        for(int i=0; i< threads.length; i++){
            threads[i] = new Thread(()->{
                int result = 0;
                for(int j=0; j<10000; j++){
                    result += j;
                }
                // ! 一步一步的 countDown 控制线程执行 灵活
                // 一个线程里可以 countDown N 多下
                latch.countDown();
            });
        }
        for(int i=0; i< threads.length; i++){
            threads[i].start();
        }
        try {
            // ! 等减到 0 然后继续执行
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("end latch ...");
    }

    private static void usingJoin(){
        Thread[] threads = new Thread[100];
        System.out.println("start join");
        for(int i=0; i< threads.length; i++){
            threads[i] = new Thread(()->{
                int result = 0;
                for(int j=0; j<10000; j++){
                    result += j;
                }
            });
        }
        for(int i=0; i< threads.length; i++){
            threads[i].start();
        }
        for(Thread o : threads){
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end join");
    }
}
