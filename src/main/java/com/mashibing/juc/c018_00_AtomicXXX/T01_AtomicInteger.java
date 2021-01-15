package com.mashibing.juc.c018_00_AtomicXXX;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 解决同样问题的更高效的方法，使用 AtomicXXX类
 * AtomicXXX类本身方法都是原子性的，但不能保证多个方法连续调用时原子性的
 *
 * ABA 问题，加版本号
 */
public class T01_AtomicInteger {

    AtomicInteger count = new AtomicInteger(0);

    void m(){
        for(int i=0; i<10000; i++){
            count.incrementAndGet();    // count++
        }
    }

    public static void main(String[] args) {
        T01_AtomicInteger t = new T01_AtomicInteger();
        List<Thread> threads = new ArrayList<>();
        for(int i=0; i<10; i++){
            threads.add(new Thread(t::m, "thread-" + i));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        // 注意这里的写法
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
