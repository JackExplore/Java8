package com.mashibing.juc.c026_ThreadPool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Fork Join Pool
 *
 * 分而治之
 */
public class T12_ForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 100000;
    static Random r = new Random();
    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        System.out.println("The Right Result: " + Arrays.stream(nums).sum());  // stream api
    }

    // 有返回值的
    static class AddTask extends RecursiveTask<Long> {
        int start, end;
        AddTask(int s, int e) {
            start = s;
            end = e;
        }
        @Override
        protected Long compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];

                }
                System.out.println("From : " + start + " To: " + end + " = " + sum);
                return sum;
            } else {
                int middle = start + (end - start) / 2;

                AddTask subTask1 = new AddTask(start, middle);
                AddTask subTask2 = new AddTask(middle, end);
                ForkJoinTask<Long> fork1 = subTask1.fork();
                ForkJoinTask<Long> fork2 = subTask2.fork();
                return fork1.join() + fork2.join();
            }
        }
    }
/*  static class AddTask extends RecursiveAction {
        int start, end;
        AddTask(int s, int e) {
            start = s;
            end = e;
        }
        @Override
        protected void compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];

                }
                System.out.println("From : " + start + " To: " + end + " = " + sum);
            } else {
                int middle = start + (end - start) / 2;

                AddTask subTask1 = new AddTask(start, middle);
                AddTask subTask2 = new AddTask(middle, end);
                subTask1.fork();
                subTask2.fork();
            }
        }
    }
*/
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        ForkJoinPool fjp = new ForkJoinPool();

        AddTask task = new AddTask(0, nums.length);
        ForkJoinTask<Long> result = fjp.submit(task);
        System.out.println("result : " + result.get());
    }
}
