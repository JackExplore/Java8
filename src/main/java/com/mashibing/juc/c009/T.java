package com.mashibing.juc.c009;

public class T {

    /**
     * 可重入锁
     */
    synchronized void m1(){
        System.out.println("m1 start");
        m2();
        System.out.println("m1 end");
    }

    synchronized void m2(){
        System.out.println("this is m2()");
    }

    public static void main(String[] args) {
        new T().m1();
    }
}
