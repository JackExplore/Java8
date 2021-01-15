package com.mashibing.juc.c024_FromVectorToQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * 有 N 张火车票，每张票都有一个编号
 * 同时有 10 个窗口对外售票
 * 请写一个模拟程序
 * <p>
 * 分析下面的程序可能会产生哪些问题？
 *
 * 有问题，超卖，最后输出都是 null
 */
public class TicketSeller1 {

    static int num = 10000;
    static List<String> tickets = new ArrayList<>();

    static {
        for (int i = 0; i < num; i++) {
            tickets.add("票编号: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while(tickets.size() > 0){
                    System.out.println("销售了--" + tickets.remove(0));
                }
            }).start();
        }
    }
}
