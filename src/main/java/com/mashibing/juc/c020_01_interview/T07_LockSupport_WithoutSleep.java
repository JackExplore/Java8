package com.mashibing.juc.c020_01_interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 检验 06 是否有问题，答案：OK
 *
 * 可以读 CountDownLatch 的源码
 */
public class T07_LockSupport_WithoutSleep {


    List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) throws InterruptedException{
        int count = 5;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        Thread t1 = new Thread(()->{
            System.out.println("t1 begin...");
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                countDownLatch.countDown();
                if(i == count-1){
                    LockSupport.park();
                }
            }
            System.out.println("t1 end");
        }, "t1");

        Thread t2 = new Thread(()->{
            try {
                System.out.println("t2 begin...");
                countDownLatch.await();
                System.out.println("t2 ok");
                System.out.println("t2 end");
                LockSupport.unpark(t1);
            } catch (InterruptedException e) {
                System.out.println("break;");
                e.printStackTrace();
            }
        }, "t2");

        t1.start();

        TimeUnit.SECONDS.sleep(5);

        t2.start();
    }
}
