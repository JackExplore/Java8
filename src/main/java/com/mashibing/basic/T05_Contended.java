package com.mashibing.basic;

import sun.misc.Contended;

/**
 * ������������ж�ȡʵ��
 * ע�����ע�⣺
 *  -XX:-RestrictContended
 */
public class T05_Contended {

//    @Contended
    volatile long x;
//    @Contended
    volatile long y;

    public static void main(String[] args) throws Exception{

        T05_Contended t = new T05_Contended();
        long times = 10000_0000L;

        Thread t1 = new Thread(() -> {
            for (long i = 0; i < times; i++) {
                t.x = i;
            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < times; i++) {
                t.y = i;
            }
        });

        long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long end = System.nanoTime();
        System.out.println("��ʱ(ms)��" + (end - start) / 1000_000);
    }
}
