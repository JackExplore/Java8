package com.mashibing.juc.c026_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T11_WorkStealingPool {

    public static void main(String[] args) {

        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());

        service.execute(new R(1000));
    }

    static class R implements Runnable {
        int time;
        R(int t){

        }
        @Override
        public void run() {

        }
    }
}
