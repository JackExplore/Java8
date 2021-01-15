package com.mashibing.juc.c022_Reference;

/**
 * Java 的四种引用：  强、软、弱、虚
 *
 * 强引用：普通的 new 操作就是强引用；
 *
 *
 */
public class M {

    /**
     * 垃圾回收会调用这个方法，为了面试造火箭而重写
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize");
    }
}
