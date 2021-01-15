package com.mashibing.jvm.c3_jmm;

/**
 * JVM 指令
 * bipush
 * istore
 * innc
 *
 */
public class TestIPlusPlus {

    public static void main(String[] args) {

        int i = 8;

        i = i++;

        System.out.println(i);  // 9

        int j = 10;
        System.out.println(j);
        j++;

    }

    public void m(int k){
        int i = 65555;
    }
}
