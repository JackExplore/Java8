package com.mashibing.juc.c026_ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class T09_FixedThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // A Compare SEQ.Exe
        long start = System.currentTimeMillis();
        getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        final int cpuCoreNum = 4;

        // B Compare Paralle.Exe
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);
        System.out.println(service);
        MyTask t1 = new MyTask(1, 80000);
        MyTask t2 = new MyTask(80000, 120000);
        MyTask t3 = new MyTask(120000, 150000);
        MyTask t4 = new MyTask(150000, 180000);
        MyTask t5 = new MyTask(180000, 200000);

        Future<List<Integer>> f1 = service.submit(t1);
        Future<List<Integer>> f2 = service.submit(t2);
        Future<List<Integer>> f3 = service.submit(t3);
        Future<List<Integer>> f4 = service.submit(t4);
        Future<List<Integer>> f5 = service.submit(t5);

        start = System.currentTimeMillis();
        f1.get();
        f2.get();
        f3.get();
        f4.get();
        f5.get();
        end = System.currentTimeMillis();

        System.out.println(end - start);

        service.shutdown();

    }

    private static List<Integer> getPrime(int from, int to) {
        List<Integer> results = new ArrayList<>();
        for (int i = from; i < to; i++) {
            if(isPrime(i))  results.add(i);
        }
        return results;
    }

    private static boolean isPrime(int num) {
        for (int j = 2; j <= num / 2; j++) {
            if (num % j == 0) return false;
        }
        return true;
    }

    private static class MyTask implements Callable<List<Integer>> {
        int start;
        int end;

        public MyTask(int i, int j) {
            start = i;
            end = j;
        }

        @Override
        public List<Integer> call() throws Exception {
            return getPrime(start, end);
        }
    }
}
