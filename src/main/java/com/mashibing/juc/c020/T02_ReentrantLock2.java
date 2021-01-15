package com.mashibing.juc.c020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 ReentrantLock 必须手动释放锁
 * 必须，必须，必须，使用 synchronized 锁定的话，如果遇到异常，JVM 会自动释放锁，但是 lock 必须手动释放锁
 */
public class T02_ReentrantLock2 {

    Lock lock = new ReentrantLock();    // 可以替换 synchronized 同下方示例

    void m1() {
        try {
            lock.lock();      // synchronized (this)

            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
                m2();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  // 一定要 finally
        }
    }

    void m2() {
        try {
            lock.lock();
            System.out.println("m2()");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        T02_ReentrantLock2 tl = new T02_ReentrantLock2();
        new Thread(tl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(tl::m2).start();
    }
}
