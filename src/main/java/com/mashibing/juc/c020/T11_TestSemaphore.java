package com.mashibing.juc.c020;

import java.util.concurrent.Semaphore;

/**
 * 信号量
 * \
 * 限流 / 收费站
 * 控制多少个线程同时执行
 *
 */
public class T11_TestSemaphore {

    public static void main(String[] args) {

        // 允许多少个线程同时执行，允许设置是否公平锁
//        Semaphore s = new Semaphore(2);
        Semaphore s = new Semaphore(1, true);

        new Thread(() -> {
            try {
                s.acquire();    // 阻塞方法

                System.out.println("T1 running...");
                Thread.sleep(200);
                System.out.println("T1 running OK");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
            }
        }).start();

        new Thread(() -> {
            try {
                s.acquire();    // 阻塞方法

                System.out.println("T2 running...");
                Thread.sleep(200);
                System.out.println("T2 running OK");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
            }
        }).start();
    }
}
