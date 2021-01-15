package com.mashibing.juc.c001;

public class T {

    private int count = 10;

//    private Object o = null;
    private Object o = new Object();
    public void m(){
        synchronized (o){
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    public static void main(String[] args) {
        T t = new T();
        System.out.println(t.count);
        t.m();
    }
}
