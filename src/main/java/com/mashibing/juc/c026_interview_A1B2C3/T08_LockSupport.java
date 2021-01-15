package com.mashibing.juc.c026_interview_A1B2C3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 推荐这种方式
 */
public class T08_LockSupport {

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) throws InterruptedException {

        char[] aI = "123456789".toCharArray();
        char[] aC = "ABCDEFGHI".toCharArray();

        t1 = new Thread(() -> {
            for (char c : aI) {
                System.out.print(c);
                LockSupport.unpark(t2); // 叫醒 t2
                LockSupport.park();     // t1 阻塞
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (char c : aC) {
                LockSupport.park();     // t2 阻塞
                System.out.println(c);
                LockSupport.unpark(t1); // 叫醒 t1
            }
        }, "t2");

        t1.start();
//        TimeUnit.SECONDS.sleep(1);
        t2.start();
    }
}
