package com.mashibing.jvm.c4_RuntimeArea;

/**
 * InvokeSpecial    可以直接定位的，不需要多态的方法
 * 1 构造方法
 * 2 private 方法
 */
public class T03_InvokeSpecial {

    public static void main(String[] args) {
        T03_InvokeSpecial t = new T03_InvokeSpecial();
        t.m();
        t.n();
    }

    public final void m(){

    }

    /**
     * Invoke special
     */
    private void n(){

    }
}
