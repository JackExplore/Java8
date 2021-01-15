package com.mashibing.juc.c022_Reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * 虚引用：管理堆外内存的
 *
 * 基本没用，就不是给程序员用的，
 * 是给写虚拟机的人用的，是给写 JVM 的人用的
 */
public class T04_PhantomReference {

    private static final List<Object> LIST = new LinkedList<>();

    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {

        // 虚幻的引用，只是为了回收时给出一个通知，通知的时候放在队列里，写 JVM 的人拿来用，被回收时做出相应的处理
        /**
         * Netty
         *
         * Java 里面的内存直接回收：Unsafe 直接操作内存
         *
         */
        PhantomReference<M> phantomReference = new PhantomReference<>(new M(), QUEUE);

        new Thread(()->{
            while(true){
                LIST.add(new byte[1024*1024]);
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                // 虚引用 get() 不到
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(()->{
            while(true){
                Reference<? extends M> poll = QUEUE.poll();
                if(poll != null){
                    System.out.println("------------- 虚引用对象被 JVM 回收了 -------------" + poll);
                }
            }
        }).start();
    }

}
