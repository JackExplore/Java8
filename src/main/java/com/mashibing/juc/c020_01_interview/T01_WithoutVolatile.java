package com.mashibing.juc.c020_01_interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 这样不可以实现
 * 1 没有加同步
 * 2 永远没有检测到怎么办
 * 3 线程之间不可见怎么办
 *
 * ArrayList 不是线程安全的
 *
 */
public class T01_WithoutVolatile {

    List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {

        T01_WithoutVolatile c = new T01_WithoutVolatile();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add " + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        /**
         *  = 5 时，检测到
         */
        new Thread(()->{
            while(true){
                if(c.size() == 5){
                    System.out.println("size = 5 break;");
                    break;
                }
            }
        }, "t2").start();
    }
}
