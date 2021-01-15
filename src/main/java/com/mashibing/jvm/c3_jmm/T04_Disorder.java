package com.mashibing.jvm.c3_jmm;

/**
 * 证明指令重排的示例：出现 (0, 0) 的情况
 * out:
 * The 314421 times (0, 0)
 *
 * 加屏障：
 * 1 JVM 级别内存屏障，是各种屏障的组合：synchronized / volatile / lock ...
 *      但，JVM级别的内存屏障并不一定依赖于硬件级别的内存屏障，还可能依赖于CPU或硬件级别的一些指令，比如lock指令等
 * 2 CPU级别的内存屏障 : 读屏障，写屏障，读写屏障
 * 3 汇编 lock 指令 等
 */
public class T04_Disorder {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;

            Thread one = new Thread(() -> {
                shortWait(1000);    // 等待时长，意义不大
                a = 1;
                x = b;
            });
            Thread other = new Thread(() -> {
                b = 1;
                y = a;
            });

            one.start();
            other.start();
            one.join();
            other.join();

            String result = "The " + i + " times (" + x + ", " + y + ")";
            if (x == 0 && y == 0) {
                System.err.println(result);
                break;
            } else {
                //
            }

        }
    }

    public static void shortWait(long interval) {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (start + interval >= end);
    }
}
