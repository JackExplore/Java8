package com.mashibing.juc.c026_interview_A1B2C3;

import java.util.concurrent.Exchanger;

/**
 * 并没有执行出结果 NOT WORK
 */
public class T12_Exchanger {

    private static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(()->{
            for (char i = 'A'; i < 'I'; i++) {
                System.out.print(i);
                try {
                    exchanger.exchange("t2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        new Thread(()->{
            for (char i = '1'; i < '9'; i++) {
                try {
                    exchanger.exchange("t1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(i + "");
            }
        }).start();


    }
}
