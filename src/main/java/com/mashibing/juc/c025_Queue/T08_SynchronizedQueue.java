package com.mashibing.juc.c025_Queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 手递手的递东西，中间的 queue 是不存在的
 *
 * 看起来没有用到，但是在线程池中用到的地方特别多
 */
public class T08_SynchronizedQueue {    // 容量为 0

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> strs = new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.put("aaa");    // 如果没有取，就永远等着，阻塞

//        strs.add("aaa");      // 不能装东西，只能阻塞式的调用

        System.out.println(strs.size());
    }
}
