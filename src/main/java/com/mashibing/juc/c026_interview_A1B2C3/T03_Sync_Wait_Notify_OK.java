package com.mashibing.juc.c026_interview_A1B2C3;

import java.util.concurrent.TimeUnit;

/**
 * 这个版本要会背
 */
public class T03_Sync_Wait_Notify_OK {

    private static volatile boolean t2Started = false;

    public static void main(String[] args) throws InterruptedException {

        final Object obj = new Object();

        char[] aI = "123456789".toCharArray();
        char[] aC = "ABCDEFGHI".toCharArray();

        Thread t1 = new Thread(()->{
            synchronized (obj){

                while(!t2Started){
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < aI.length; i++) {
                    System.out.print(aI[i]);
                    obj.notify();
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                obj.notify();
            }
        }, "t1");

        /**
         * t1 先启动 OK
         * t2 先启动 OK
         */
        Thread t2 = new Thread(()->{
            synchronized (obj){
                t2Started = true;
                for (int i = 0; i < aC.length; i++) {
                    obj.notify();
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print(aC[i] + " ");

                }
                obj.notify();
            }
        }, "t2");

//        t1.start();
//        TimeUnit.SECONDS.sleep(1);
//        t2.start();
        //------------------
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        t1.start();



    }
}
