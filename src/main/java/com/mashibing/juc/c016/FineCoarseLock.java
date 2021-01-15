package com.mashibing.juc.c016;

import java.util.concurrent.TimeUnit;

/**
 * synchronized 优化
 * 同步代码块中的语句越少越好
 * 锁的细化和粗化
 */
public class FineCoarseLock {

    int count = 0;

    synchronized void m1(){
        // do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 业务逻辑中需要进行同步的代码块，这时不应该给整个方法上锁
        count++;

        // do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void m2(){
        // do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 业务逻辑中需要进行同步的代码块，这时不应该给整个方法上锁
        // 锁的细化
        synchronized (this){
            count++;
        }

        // do sth need not sync
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
