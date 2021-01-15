package com.mashibing.juc.c020_01_interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 相比01，添加了 volatile 修饰 List
 *
 * 增加了List的可见性，再次添加synchronized可以解决同步问题，但感觉这个程序还是有问题的
 *
 * 其实，由于多线程2 不能保证在当时得到调用，即便 while(true)
 *
 */
public class T02_WithVolatile {


    // 添加 volatile，使线程2能够得到通知？（疑问：只是增加了可见性吧，但是否线程2会执行呢？）
    /**
     * volatile 只是说引用指向的改变，至于引用的内容的改变，是观察不到的！
     */
    volatile List lists = new ArrayList<>();
//    volatile List lists = Collections.synchronizedList(new ArrayList<>());    // 同步问题 add() size()

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {

        T02_WithVolatile c = new T02_WithVolatile();

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
