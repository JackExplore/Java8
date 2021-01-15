package com.mashibing.juc.c022_Reference;

import java.lang.ref.WeakReference;

/**
 * 弱引用：只要遭遇到 gc() 就会被回收
 *
 * 一般用在容器里，
 * 一个典型的应用，就是 ThreadLocal
 */
public class T03_WeakReference {

    public static void main(String[] args) {

        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        ThreadLocal<M> tl = new ThreadLocal<>();
        tl.set(new M());
        tl.remove();
    }
}
