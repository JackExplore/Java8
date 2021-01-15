package com.mashibing.juc.c026_interview_A1B2C3;

/**
 * 自旋式写法
 *
 * 感觉这种写法比较简单实用
 */
public class T06_cas {

    enum ReadyToRun {T1, T2}

    static volatile ReadyToRun r = ReadyToRun.T1;   // 这里使用 volatile

    public static void main(String[] args) {

        char[] aI = "123456789".toCharArray();
        char[] aC = "ABCDEFGHI".toCharArray();

        new Thread(() -> {
            for (char c : aI) {
                while(r != ReadyToRun.T2){}
                System.out.print(c);
                r = ReadyToRun.T1;
            }
        }).start();
        new Thread(() -> {
            for (char c : aC) {
                while(r != ReadyToRun.T1){}
                System.out.print(c);
                r = ReadyToRun.T2;
            }
        }).start();
    }
}
