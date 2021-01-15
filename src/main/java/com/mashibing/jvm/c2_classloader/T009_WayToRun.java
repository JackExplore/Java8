package com.mashibing.jvm.c2_classloader;

/**
 * JVM -
 * 默认   :   混合模式    315
 * -Xint :   纯解释执行   20207
 * -Xcomp:   纯编译执行   298        这里，快了一点点
 *
 */
public class T009_WayToRun {

    public static void main(String[] args) {

        for (int i = 0; i < 10_0000; i++) {
            m();
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            m();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private static void m() {
        for (long i = 0; i < 10_000L; i++) {
            long j = i % 3;
        }
    }
}
