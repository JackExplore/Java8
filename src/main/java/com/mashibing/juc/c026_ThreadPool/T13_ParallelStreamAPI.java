package com.mashibing.juc.c026_ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 流式 API
 *
 */
public class T13_ParallelStreamAPI {

    public static void main(String[] args) {

        List<Integer> nums = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            nums.add(1000000 + r.nextInt(1000000));
        }
        long start = System.currentTimeMillis();
        nums.forEach(v -> isPrime(v));
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        // 使用 parallel stream api
        start = System.currentTimeMillis();

        nums.parallelStream().forEach(T13_ParallelStreamAPI::isPrime);      // !!!!!! Parallel Stream

        end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private static List<Integer> getPrime(int from, int to) {
        List<Integer> results = new ArrayList<>();
        for (int i = from; i < to; i++) {
            if (isPrime(i)) results.add(i);
        }
        return results;
    }

    private static boolean isPrime(int num) {
        for (int j = 2; j <= num / 2; j++) {
            if (num % j == 0) return false;
        }
        return true;
    }
}
