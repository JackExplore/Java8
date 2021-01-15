package com.mashibing.jvm.c1_bytecode;

public class T0104_ByteCode04 {

    int i = 0;

    String s = "Hello ByteCode!";

    public T0104_ByteCode04(int i, String s) {
        this.i = i;
        this.s = s;

        /**
         * 这两条指令可能都不是原子性的。。。字撕裂？
         * 但 volatile 修饰的 long / double 是的! JVM Spec.
         */
        double f = 0.0f;
        long l = 8L;
    }

    public void m() {

    }
}
