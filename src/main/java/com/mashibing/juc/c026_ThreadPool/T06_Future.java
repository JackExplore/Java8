package com.mashibing.juc.c026_ThreadPool;

import java.util.concurrent.*;

/**
 * FutureTask
 */
public class T06_Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> task = new FutureTask<Integer>(()->{
            TimeUnit.SECONDS.sleep(1);
            return 1000;
        });

        new Thread(task).start();

        System.out.println(task.get());

        System.out.println("\n--------------------------\n");
        //---------------------------------------

        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> f = service.submit(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return 1;
        });
        System.out.println(f.isDone());
        System.out.println(f.get());
        System.out.println(f.isDone());
//        service.shutdownNow();  // 为什么没有自动结束？
    }
}
