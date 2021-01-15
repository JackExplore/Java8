package com.mashibing.juc.c026_interview_A1B2C3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T04_Lock_Condition {

    public static void main(String[] args) throws InterruptedException {

        char[] aI = "123456789".toCharArray();
        char[] aC = "ABCDEFGHI".toCharArray();

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

//        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
//                countDownLatch.await();
                for (char c : aI) {
                    condition.signal();
                    condition.await();
                    System.out.print(c);
                }

                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
//                countDownLatch.countDown();
                for (char c : aC) {
                    condition.signal();
                    condition.await();
                    System.out.print(c);
                }

                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t2.start();
        TimeUnit.SECONDS.sleep(1);
        t1.start();
    }
}
