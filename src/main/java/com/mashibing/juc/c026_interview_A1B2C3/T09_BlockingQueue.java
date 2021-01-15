package com.mashibing.juc.c026_interview_A1B2C3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 这种写法也很好，推荐
 */
public class T09_BlockingQueue {

    static BlockingQueue<String> q1 = new ArrayBlockingQueue<>(1);
    static BlockingQueue<String> q2 = new ArrayBlockingQueue<>(1);

    public static void main(String[] args) throws InterruptedException {

        char[] aI = "123456789".toCharArray();
        char[] aC = "ABCDEFGHI".toCharArray();


        new Thread(() -> {
            for (char c : aC) {
                try{
                    q1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(c);
                try {
                    q2.put("aa");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            for (char c : aI) {
                System.out.print(c);
                try{
                    q1.put("aa");
                    q2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
