package com.mashibing.juc.c020;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 同步工具
 * - 循环栅栏，什么时候坐满了，什么时候发车一组
 */
public class T07_TestCyclicBarrier {

    public static void main(String[] args) {

//        CyclicBarrier barrier = new CyclicBarrier(20);

        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("满人，发车"));
//        CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("人满，发车！");
//            }
//        });

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
