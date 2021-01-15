package com.mashibing.juc.c024_FromVectorToQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 有 N 张火车票，每张票都有一个编号
 * 同时有 10 个窗口对外售票
 * 请写一个模拟程序
 * <p>
 * 分析下面的程序可能会产生哪些问题？
 *
 * 有问题，也会造成同步问题
 */
public class TicketSeller2 {

    static int num = 10000;
    static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < num; i++) {
            tickets.add("票编号: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while(tickets.size() > 0){
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("销售了--" + tickets.remove(0));
                }
            }).start();
        }
    }
}
