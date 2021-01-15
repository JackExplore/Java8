package com.mashibing.juc.c020;

import book.thinkInJava.concurrent.GThreadLocal.ThreadLocalVariableHolder;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 还可以指定为公平锁，默认为非公平锁，（是否会检查等待队列）
 * 对比输出结果
 *  AQS ?
 * 而 synchronized 只有非公平锁
 */
public class T05_ReentrantLock5 implements Runnable {

    // 参数为 true 表示为 公平锁
    private static ReentrantLock lock = new ReentrantLock(true);

    public void run() {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        T05_ReentrantLock5 rl = new T05_ReentrantLock5();
        Thread th1 = new Thread(rl);
        Thread th2 = new Thread(rl);
        th1.start();
        th2.start();

    }
}
