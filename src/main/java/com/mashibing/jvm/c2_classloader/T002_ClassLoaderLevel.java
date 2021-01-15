package com.mashibing.jvm.c2_classloader;

public class T002_ClassLoaderLevel {

    public static void main(String[] args) {

        // Bootstrap ClassLoader
        System.out.println(String.class.getClassLoader());
        // Bootstrap ClassLoader
        System.out.println(sun.awt.HKSCS.class.getClassLoader());
        // Extension ClassLoader
        System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader());
        // App ClassLoader
        System.out.println(T002_ClassLoaderLevel.class.getClassLoader());


        // 往上找都是被【顶级加载器】所加载，而不是 parent 的parent ，所以，这里为 Bootstrap ClassLoader，输出为 null
        System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader().getClass().getClassLoader());
        System.out.println(T002_ClassLoaderLevel.class.getClassLoader().getClass().getClassLoader());
    }
}
