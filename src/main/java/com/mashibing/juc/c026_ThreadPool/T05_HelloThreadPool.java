package com.mashibing.juc.c026_ThreadPool;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class T05_HelloThreadPool {

    static class Task implements Runnable {
        private int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Task " + i);
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                    "i=" + i +
                    '}';
        }
    }

    public static void main(String[] args) {

        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
                2,                                 // 核心线程数
                4,                             // 最大线程数
                60,                               // 生存时间       :       空闲线程归还给 OS，核心线程永远活着（但有参数可以设置归还）
                TimeUnit.SECONDS,                              // 生存时间单位
                //
                new ArrayBlockingQueue<>(4),            // 任务队列      :       队列不同产生不同的线程池
                Executors.defaultThreadFactory(),               // 产生线程的线程工厂
                new ThreadPoolExecutor.DiscardOldestPolicy()    // 拒绝策略     :        JDK 默认提供了 四种，可以自定义
        );

        for (int i = 0; i < 8; i++) {
            tpe.execute(new Task(i));
        }

        System.out.println(tpe.getQueue());

        tpe.execute(new Task(100));         // DiscardOldestPolicy()

        System.out.println(tpe.getQueue());

        tpe.shutdown();

    }
}
