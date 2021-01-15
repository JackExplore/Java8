package com.mashibing.juc.c020_01_interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * 视频中存在启动先后的问题，但这里的写法不存在问题，可以参看 07 调整执行先后顺序后的效果
 */
public class T06_LockSupport {


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

        t2.start();
        t1.start();
    }
}
