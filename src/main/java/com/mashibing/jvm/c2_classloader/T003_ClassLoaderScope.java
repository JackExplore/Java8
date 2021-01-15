package com.mashibing.jvm.c2_classloader;

/**
 * 不同的 ClassLoader 加载的不同资源路径
 */
public class T003_ClassLoaderScope {

    public static void main(String[] args) {

        String pathBoot = System.getProperty("sun.boot.class.path");
        System.out.println(pathBoot);
        System.out.println(pathBoot.replaceAll(";", System.lineSeparator()));

        System.out.println("--------------------------------------------------------");

        String pathExt = System.getProperty("java.ext.dirs");
        System.out.println(pathExt);
        System.out.println(pathExt.replaceAll(";", System.lineSeparator()));

        System.out.println("--------------------------------------------------------");
        String pathApp = System.getProperty("java.class.path");
        System.out.println(pathApp);
        System.out.println(pathApp.replaceAll(";", System.lineSeparator()));

    }
}
