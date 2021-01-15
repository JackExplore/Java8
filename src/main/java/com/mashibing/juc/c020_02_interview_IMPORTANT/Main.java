package com.mashibing.juc.c020_02_interview_IMPORTANT;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        MyContainer1<String> c = new MyContainer1<>();
        MyContainer2<String> c = new MyContainer2<>();

        // 消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    System.out.println(Thread.currentThread().getName() + " c.get() + " + c.get());
                }
            }, "c" + i).start();
        }

        TimeUnit.SECONDS.sleep(2);

        // 生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j < 25; j++) {
                    c.put(Thread.currentThread().getName() + "  " + j);
//                    System.out.println(Thread.currentThread().getName() + " put() + " + j);
                }
            }, "p" + i).start();
        }
    }
}
