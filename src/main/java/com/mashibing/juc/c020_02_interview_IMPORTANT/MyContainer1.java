package com.mashibing.juc.c020_02_interview_IMPORTANT;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 同步容器，要会背！    没看太懂
 *
 * 叫醒了全部的生产者和消费者
 *
 * 面试题：
 *
 * 写一个固定容量的同步容器，拥有 put 和 get 方法，以及 getCount 方法
 *
 * 能够支持 2 个生产者线程 和 10 个 消费者线程阻塞调用
 *
 * 使用 wait notify / notifyAll 来实现
 */
public class MyContainer1<T> {

    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put(T t){
        while(lists.size() == MAX){     // 为什么这里用 while 而不是 if
            try{
                this.wait();            // 因为这里，醒了之后也要回来判断一遍
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lists.add(t);
        ++count;
        this.notifyAll();   // 通知消费者线程进行消费
    }

    public synchronized T get(){
        T t = null;
        while(lists.size() == 0){
            try{
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        count--;
        this.notifyAll();   // 通知生产者线程进行生产
        return t;
    }

    public static void main(String[] args) throws InterruptedException {
        MyContainer1<String> c = new MyContainer1<>();

        // 消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    System.out.println(Thread.currentThread().getName() + " c.get() + " + c.get());
                }
            }, "c" + i).start();
        }

        TimeUnit.SECONDS.sleep(2);

        // 生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j < 25; j++) {
                    c.put(Thread.currentThread().getName() + "  " + j);
//                    System.out.println(Thread.currentThread().getName() + " put() + " + j);
                }
            }, "p" + i).start();
        }
    }
}
