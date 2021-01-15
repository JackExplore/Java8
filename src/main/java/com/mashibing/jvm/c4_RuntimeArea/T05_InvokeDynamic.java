package com.mashibing.jvm.c4_RuntimeArea;

import java.util.ArrayList;

/**
 * JVM 最难指令
 * Invoke Dynamic
 * Lambda Java开始支持动态语言
 */
public class T05_InvokeDynamic {

    public static void main(String[] args) {

        /**
         * 内部类的内部类
         * 动态产生了很多 Class
         */
        I i = C::n;
        I i2 = C::n;
        I i3 = C::n;
        I i4 = ()->{
            C.n();
        };

        System.out.println(i.getClass());
        System.out.println(i2.getClass());
        System.out.println(i3.getClass());
        System.out.println(i4.getClass());

        // 如果不小心写出这样的代码？
        for(;;) {I j = C::n;} // MethodArea   < 1.8  Perm Space (FGC不回收)
    }

    @FunctionalInterface
    public static interface I {
        void m();
    }

    public static class C {
        static ArrayList<int[]> list = new ArrayList<>();
        static void n() {
            System.out.println("hello");
            list.add(new int[1000]);
        }
    }
}
