package com.mashibing.juc.c020_01_interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Join ?
 * LockSupport 起作用要保证线程已启动，但并不需要已经 park()
 */
public class T08_Join {


    List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) throws InterruptedException{

        Thread t1, t2;

        t2 = new Thread(()->{
            LockSupport.park();
            System.out.println("t2 begin...");
            System.out.println("t2 ok");
            System.out.println("t2 end");
        }, "t2");

        t1 = new Thread(()->{
            System.out.println("t1 begin...");
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
            }

            try {
                LockSupport.unpark(t2);
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 5; i < 10; i++) {
                System.out.println(i);
            }
            System.out.println("t1 end");
        }, "t1");

        t1.start();

//        TimeUnit.SECONDS.sleep(5);

        t2.start();
    }
}
