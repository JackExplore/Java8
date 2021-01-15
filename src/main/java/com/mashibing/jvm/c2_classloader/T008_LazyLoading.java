package com.mashibing.jvm.c2_classloader;

/**
 * 注意 final 变量不需要初始化 class 对象 !!!
 */
public class T008_LazyLoading {

    public static class P {
        final static int i = 8;
        static int j = 9;

        static {
            System.out.println("P");
        }
    }

    public static class X extends P {
        static {
            System.out.println("X");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        P p;
//        X x = new X();
//        System.out.println(P.i);
//        System.out.println(P.j);
        Class.forName("com.mashibing.jvm.c2_classloader.T008_LazyLoading$P");
    }
}
