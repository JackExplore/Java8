package com.mashibing.juc.c022_ThreadLocal;

import java.util.concurrent.TimeUnit;

public class ThreadLocal1 {

    volatile static Person p = new Person();

    public static void main(String[] args) {

        new Thread(()->{
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " / " + p.name);
        }).start();

        new Thread(()->{
            try{
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.name = "lisi";
            System.out.println(Thread.currentThread().getName() + " / " + p.name);

        }).start();
    }

}

class Person {

    String name;

    public Person(){}
    public Person(String n) {
        name = n;
    }
}
