package com.mashibing.juc.c022_Reference;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * 软引用
 *
 * : 当有一个对象被软引用指向，当系统内容不够用的时候才会回收它
 * 内存不够用了，就把软引用干掉
 *
 * 主要在缓存中用，比如，读取了一大堆图片
 * memoryCache
 */
public class T02_SoftReference {

    public static void main(String[] args) throws InterruptedException {

        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        // m = null;
        System.out.println(m.get());

        System.gc();    // 进行回收

        TimeUnit.SECONDS.sleep(1);

        System.out.println(m.get());

        byte[] b = new byte[1024*1024*10];
        System.out.println(m.get());
    }
}
