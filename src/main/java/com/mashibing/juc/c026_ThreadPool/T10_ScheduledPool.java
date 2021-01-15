package com.mashibing.juc.c026_ThreadPool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时器框架： Quartz
 * JDK定时器： Timer
 * 定时任务线程池：ScheduledPool
 */
public class T10_ScheduledPool {

    public static void main(String[] args) {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.scheduleAtFixedRate(()->{
            try{
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, 0, 500, TimeUnit.MILLISECONDS);

    }
}
