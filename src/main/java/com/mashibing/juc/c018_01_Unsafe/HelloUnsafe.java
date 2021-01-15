package com.mashibing.juc.c018_01_Unsafe;

import sun.misc.Unsafe;

public class HelloUnsafe {

    static class M {
        private M() {
        }
        int i = 0;
    }

    /**
     * 编译通过，但运行报错，不让用
     * @param args
     */
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        M m = null;
        try {
            m = (M)unsafe.allocateInstance(M.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        m.i = 9;
        System.out.println(m.i);
    }
}
