package com.mashibing.juc.c008;

import java.util.concurrent.TimeUnit;

/**
 * 面试题：模拟银行账户
 * 对业务写方法-加锁
 * 对业务读方法-不加锁
 * 这样行不行？
 */
public class Account {

    String name;
    double balance;

    public synchronized void set(String name, double balance){
        System.out.println("SETTING ACCOUNT START");
        this.name = name;
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        this.balance = balance;
        System.out.println("SETTING ACCOUNT END");
    }

    // 允不允许脏读 ?
    public /*synchronized*/ double getBalance(String name){
        return this.balance;
    }

    public static void main(String[] args) {
        Account a = new Account();
        new Thread(()->a.set("AAA", 100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(a.name + " /READ " + a.getBalance(a.name));
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(a.name + " /READ " + a.getBalance(a.name));
    }
}
