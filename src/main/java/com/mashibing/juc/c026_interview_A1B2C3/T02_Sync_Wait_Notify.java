package com.mashibing.juc.c026_interview_A1B2C3;

import java.util.concurrent.TimeUnit;

public class T02_Sync_Wait_Notify {

    public static void main(String[] args) throws InterruptedException {

        final Object o = new Object();

        char[] aI = "123456789".toCharArray();
        char[] aC = "ABCDEFGHI".toCharArray();

        Thread t1 = new Thread(() -> {
            synchronized (o) {
                for (int i = 0; i < aI.length; i++) {
                    System.out.print(aI[i] + "");
                    o.notify();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notifyAll();
            }
            System.out.println("\n" + Thread.currentThread().getName() + " OK !");
        });

//        TimeUnit.SECONDS.sleep(1);

        Thread t2 = new Thread(() -> {
            synchronized (o) {
                for (int i = 0; i < aC.length; i++) {
                    System.out.print(aC[i] + "");
                    o.notify();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                o.notifyAll();
            }
            System.out.println("\n" + Thread.currentThread().getName() + " OK !");
        });

        t1.start();
        t2.start();
    }
}
