package com.mashibing.juc.c026_ThreadPool;

import java.util.concurrent.*;

public class T14_MyRejectedHandler {

    public static void main(String[] args) {
        ExecutorService service = new ThreadPoolExecutor(4, 4,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(6), Executors.defaultThreadFactory(),
                new MyHandler());
        for (int i = 0; i < 20; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
    }

    static class MyHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Logging this operation ...");
            // save(this); r kafka mysql redis
            // try 3 times
            if(executor.getQueue().size() < 10000){
                // try put again
                System.out.println("Can do sth.");
            }
        }
    }
}
