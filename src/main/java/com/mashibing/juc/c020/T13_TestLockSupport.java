package com.mashibing.juc.c020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 阻塞和唤醒指定线程
 */
public class T13_TestLockSupport {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(()->{
            for (int i = 0; i <10; i++) {
                System.out.println(i);
                if(i == 5){
                    LockSupport.park();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

        TimeUnit.SECONDS.sleep(1);
//        System.out.println("after 8 secs");
        LockSupport.unpark(t);
        System.out.println("unpark()");
//
//        LockSupport.park(t);
//        System.out.println("park() out");




    }
}
