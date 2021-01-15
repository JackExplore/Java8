package com.mashibing.juc.c017;

import java.util.concurrent.TimeUnit;

/**
 * 锁定某个对象 o , 如果 o 的属性发生改变，不影响锁的使用
 * 但是如果 o 变成另外一个对象，则锁定的对象发生改变，应避免
 */
public class T {

    final Object o = new Object();

    void m(){
        synchronized (o){
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        T t = new T();
        Thread t1 = new Thread(t::m, "T1");
        Thread t2 = new Thread(t::m, "T2");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        t.o = new Object();

        t2.start();
    }
}
