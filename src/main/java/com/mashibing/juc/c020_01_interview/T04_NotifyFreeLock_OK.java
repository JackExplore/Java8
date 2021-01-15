package com.mashibing.juc.c020_01_interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 深入理解这个程序
 *
 * wait 释放锁
 * notify 通知wait等待队列，但不释放锁!@
 *
 * 这里使用 wait 和 notify 做到，wait 会释放锁，而 notify 不会释放锁，
 * 需要注意的是，运用这种方法，必须要保证 t2 先执行，也就是首先让 t2 监听才可以。
 *
 *
 */
public class T04_NotifyFreeLock_OK {
    List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) throws InterruptedException {

        T04_NotifyFreeLock_OK c = new T04_NotifyFreeLock_OK();

        final Object lock = new Object();

        new Thread(() -> {
            System.out.println("t2 start");
            synchronized (lock) {
                if (c.size() != 5) {
                    try {
                        lock.wait();    // wait 回来，必须要拿到这把锁，才能继续执行
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("t2 notify() ok");
                    lock.notify();    // 这句话不写程序会卡在这
                }
            }
            System.out.println("t2 end");
        }, "t2").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            System.out.println("t1 start");
            synchronized (lock) {   // 这里注意锁的位置
                for (int i = 0; i < 10; i++) {
                    // sync 不是这里
                    c.add(i);
                    System.out.println("add " + i);
                    if (c.size() == 5) {
                        lock.notify();  // 不释放锁

                        // 释放锁，让 t2 得以执行
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("t1 end");
        }, "t1").start();
    }
}
