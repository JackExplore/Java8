package com.mashibing.juc.c021_AQS;

import java.util.concurrent.locks.ReentrantLock;


/**
 * AQS - volatile & CAS
 *
 * state & Node
 *
 * 使用 CAS 操作 state node/head,tail 的操作取代了 synchronized 锁定整体的操作，所以效率高
 *
 * 为什么是双向链表？    因为后面的结点需要查看一下前面结点的状态
 */
public class TestReentrantLock {

    private static volatile int i = 0;

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        lock.lock();

        i++;

        lock.unlock();
    }
}
