package com.mashibing.juc.c022_ThreadLocal;

import java.util.concurrent.TimeUnit;

public class ThreadLocal2 {

    static ThreadLocal<Person> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(()->{
            Person p = new Person("zhangsan");
            threadLocal.set(p);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " / " + threadLocal.get().name);
        }, "t1").start();

        new Thread(()->{
            Person p = new Person("lisi");
            threadLocal.set(p);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " / " + threadLocal.get().name);
        }, "t2").start();
    }
}

