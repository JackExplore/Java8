package com.mashibing.juc.c020;

import java.util.concurrent.TimeUnit;

/**
 * ReentrantLock 内部用的依然是 CAS ?
 *
 * 锁升级的概念，前面有一个 CAS 的状态
 *
 * lock.park()  unpark()
 */
public class T01_ReentrantLock1 {

    synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            m2();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("" + i);
        }
    }

    synchronized void m2() {
        System.out.println("m2...");
    }

    public static void main(String[] args) {
        T01_ReentrantLock1 tl = new T01_ReentrantLock1();
        new Thread(tl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(tl::m2).start();
    }
}
