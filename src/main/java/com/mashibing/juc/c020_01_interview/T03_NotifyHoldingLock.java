package com.mashibing.juc.c020_01_interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * wait notify
 *
 * 但是，这样写法不对
 */
public class T03_NotifyHoldingLock {

    List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) throws InterruptedException {

        T03_NotifyHoldingLock c = new T03_NotifyHoldingLock();

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
                }
            }
            System.out.println("t2 end");
        }, "t2").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            System.out.println("t1 start");
            for (int i = 0; i < 10; i++) {
                synchronized (lock) {
                    c.add(i);
                    System.out.println("add " + i);
                    if (c.size() == 5) {
                        lock.notify();  // 不释放锁
                    }
                }
            }
            System.out.println("t1 end");
        }, "t1").start();
    }
}
