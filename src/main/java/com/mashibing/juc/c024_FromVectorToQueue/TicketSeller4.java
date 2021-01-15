package com.mashibing.juc.c024_FromVectorToQueue;

import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

/**
 * 有 N 张火车票，每张票都有一个编号
 * 同时有 10 个窗口对外售票
 * 请写一个模拟程序
 * <p>
 * 分析下面的程序可能会产生哪些问题？
 *
 * 推荐，没问题，Queue 就是为了多线程/高并发使用
 */
public class TicketSeller4 {

    static int num = 10000;
    static Queue<String> tickets = new ConcurrentLinkedDeque<>();

    static {
        for (int i = 0; i < num; i++) {
            tickets.add("票编号: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String s = tickets.poll();
                    if (s == null) break;
                    else {
                        System.out.println("销售了--" + s);
                    }
                }
            }).start();
        }
    }
}
