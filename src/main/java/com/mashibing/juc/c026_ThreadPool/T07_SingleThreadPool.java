package com.mashibing.juc.c026_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors 线程池的工厂
 *
 * 为什么要有单线程的线程池？
 * 1、线程池是有任务队列的
 * 2、生命周期管理，线程池提供
 * 3、
 */
public class T07_SingleThreadPool {

    public static void main(String[] args) {

        ExecutorService service = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            final int j = i;
            service.execute(()->{
                System.out.println(j + " " + Thread.currentThread().getName());
            });
        }

        service.shutdown();
    }
}
