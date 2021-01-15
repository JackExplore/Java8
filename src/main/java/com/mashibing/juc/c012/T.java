package com.mashibing.juc.c012;

import java.util.concurrent.TimeUnit;

public class T {

    /*volatile*/ boolean running = true;    // 对比一下有无 volatile 的情况下，整个程序运行结果的区别

    void m() {
        System.out.println("m start");

        while(running){
//            try {
//                TimeUnit.MILLISECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("m end");
    }

    public static void main(String[] args) throws Exception{

        T t = new T();

        new Thread(t::m, "t1").start();
        /**
         * lambda 表达式： new Thread(new Runnable(run(){m();}));
         */

        TimeUnit.SECONDS.sleep(1);

        t.running = false;
    }
}
