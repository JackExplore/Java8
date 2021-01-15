package com.mashibing.juc.c022_Reference;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 强引用
 */
public class T01_NormalReference {

    public static void main(String[] args) throws IOException, InterruptedException {

        M m = new M();

        m = null;

        TimeUnit.SECONDS.sleep(3);

        System.gc();

        // 阻塞住当前线程
        System.out.println(System.in.read());
    }
}
