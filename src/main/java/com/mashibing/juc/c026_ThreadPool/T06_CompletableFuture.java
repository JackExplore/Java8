package com.mashibing.juc.c026_ThreadPool;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 一堆任务的管理类
 *
 *
 */
public class T06_CompletableFuture {

    public static void main(String[] args) throws IOException {

        long start, end;

        start = System.currentTimeMillis();

        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(() -> priceOfTM());
        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(() -> priceOfTB());
        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(() -> priceOfJD());

        // 提供了对一堆任务的管理
        CompletableFuture.allOf(futureTM, futureTB, futureJD).join();


        end = System.currentTimeMillis();
        System.out.println("use completable future !" + (end - start));

        CompletableFuture.supplyAsync(() -> priceOfTM())
                .thenApply(String::valueOf)
                .thenApply(str -> "price " + str)
                .thenAccept(System.out::println);

//        System.in.read();   // 阻塞住，使得上面的异步调用得以展示
    }

    private static double priceOfTM() {
        delay();
        return 1.0;
    }

    private static double priceOfTB() {
        delay();
        return 2.0;
    }

    private static double priceOfJD() {
        delay();
        return 3.0;
    }

    private static double priceOfAmazon() {
        delay();
        throw new RuntimeException();
    }

    private static void delay() {
        int time = new Random().nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("After %s sleep!\n", time);
    }
}
