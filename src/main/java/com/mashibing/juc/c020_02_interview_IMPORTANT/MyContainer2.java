package com.mashibing.juc.c020_02_interview_IMPORTANT;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 叫醒指定的角色
 *
 * wait / notify
 *
 * await / signal
 */
public class MyContainer2<T> {

    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    private Lock lock = new ReentrantLock();
    /**
     * Condition 的本质就是不同的等待队列
     */
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();


    public void put(T t){
        try{
            lock.lock();
            while(lists.size() == MAX){ // 想想为什么用 while 而不是 if
                producer.await();
            }
            lists.add(t);
            ++count;
            consumer.signalAll();   // 通知消费者线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public T get(){
        T t = null;
        try{
            lock.lock();
            while(lists.size() == 0){
                consumer.await();
            }
            t = lists.removeFirst();
            count--;
            producer.signalAll();   // 通知生产者进行生产
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return t;
    }

    public static void main(String[] args) {

    }
}
