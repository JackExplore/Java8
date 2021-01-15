package com.mashibing.juc.c014;

import java.util.ArrayList;
import java.util.List;

/**
 * synchronized 可以保证 可见性 和 原子性，volatile 只能保证可见性
 *
 * 解决 13 的办法  加 synchronized
 */
public class T {

    /**
     * 保证了 可见性 而并非 原子性
     */
    volatile int count = 0;

    /**
     * 加 synchronized
     */
    synchronized void m(){
        for(int i=0; i<1000; i++){
            count++;
        }
    }

    public static void main(String[] args) {
        T t = new T();
        List<Thread> threads = new ArrayList<>();
        for(int i=0; i<10; i++){
            threads.add(new Thread(t::m, "thread-" + i));
        }
        threads.forEach((o)->o.start());

        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.count);
    }
}
