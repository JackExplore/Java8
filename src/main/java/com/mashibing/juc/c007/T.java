package com.mashibing.juc.c007;

/**
 * synchronized 和 非同步方法能否同时访问
 */
public class T {

    public synchronized void m1(){
        /**
         * ----
         */
    }

    public void m2(){
        /**
         *
         */
    }

    public static void main(String[] args) {
        T t = new T();

        new Thread(t::m1, "t1").start();
        new Thread(t::m2, "t2").start();
    }
}
