package com.mashibing.juc.c018;

/**
 * 不要使用 String 类型来加锁
 */
public class T {

    String s1 = "hello";
    String s2 = "hello";

    void m1(){
        synchronized (s1){

        }
    }
    void m2(){
        synchronized (s2){

        }
    }

    public static void main(String[] args) {

    }

}
