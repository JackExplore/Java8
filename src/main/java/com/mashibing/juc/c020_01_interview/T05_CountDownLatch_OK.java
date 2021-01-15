package com.mashibing.juc.c020_01_interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 使用 countDownLatch 替代 wait/notify 来进行通知
 * 好处是通信方式简单，同时也可以指定等待时间
 * 使用 await 和 countDown 方法替代 wait 和 notify
 * countDownLatch 不涉及锁定，当 count 的值减为 0 时，当前线程继续运行
 *
 * 当不涉及同步，只是涉及线程通信的时候，用 synchronized + wait/notify 就显得繁琐
 * 这时，应该考虑使用 countDownLatch / cyclicBarrier / semaphore
 *
 * 思考该怎么做这个实验
 *
 * 这里存在的问题：t1 之后继续执行了，不能保证 t2 执行完成，再继续 t1 往下执行
 *
 * 解决方案：两只门栓 + countDownLatch1
 */
public class T05_CountDownLatch_OK {

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
        CountDownLatch countDownLatch1 = new CountDownLatch(1);

        Thread t1 = new Thread(()->{
            System.out.println("t1 begin...");
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                countDownLatch.countDown();
                if(i == count-1){
                    try {
                        countDownLatch1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                countDownLatch1.countDown();
            } catch (InterruptedException e) {
                System.out.println("break;");
                e.printStackTrace();
            }
        }, "t2");

        t2.start();
        t1.start();
    }
}
