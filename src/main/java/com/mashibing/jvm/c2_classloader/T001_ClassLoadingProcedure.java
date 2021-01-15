package com.mashibing.jvm.c2_classloader;

/**
 * loading
 *
 * linking : verification preparation resolution
 *
 * initializing
 */
public class T001_ClassLoadingProcedure {

    public static void main(String[] args) {
        System.out.println(T.count);
    }
}

class T {
    public static int count = 2;    // 0
    public static T t = new T();    // null

    private T() {
        count++;
    }
}