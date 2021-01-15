package com.mashibing.juc.c020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * tryLock(time,unit);
 */
public class T03_ReentrantLock3 {

    Lock lock = new ReentrantLock();

    void m1() {
        try {
            lock.lock();

            for (int i = 0; i < 3; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  // 一定要 finally
        }
    }

    /**
     * 可以使用 tryLock() 进行锁定，由于 tryLock(time)抛出异常，所以要注意 unlock 的判定条件
     */
    void m2() {
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2()..." + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked)
                lock.unlock();
        }
    }

    public static void main(String[] args) {

        T03_ReentrantLock3 tl = new T03_ReentrantLock3();
        new Thread(tl::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(tl::m2).start();
    }
}
