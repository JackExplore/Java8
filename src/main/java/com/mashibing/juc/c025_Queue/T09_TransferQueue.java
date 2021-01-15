package com.mashibing.juc.c025_Queue;

import java.util.concurrent.LinkedTransferQueue;

/**
 * 适用场景：
 * 做了一件事情有结果，必须需要这个结果才能够进行下面的流程，比如支付流程
 */
public class T09_TransferQueue {

    public static void main(String[] args) throws InterruptedException {

        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

        new Thread(()->{
            try{
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();     // 必须先启动消费者线程

        strs.transfer("aaa");   // 上来就等着

    }
}
