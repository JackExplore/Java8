package com.mashibing.juc.c010_super_class_sync;

import java.util.concurrent.TimeUnit;

public class T {

    synchronized void m() throws InterruptedException {
        System.out.println("m start");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("m end");
    }

    public static void main(String[] args) throws InterruptedException {
        new TT().m();
    }
}

/**
 * 调用父类，锁的还是同一把锁，同可重入锁
 */
class TT extends T{
    @Override
    synchronized void m() throws InterruptedException {
        System.out.println("child m start");
        super.m();
        System.out.println("child m end");
    }
}
