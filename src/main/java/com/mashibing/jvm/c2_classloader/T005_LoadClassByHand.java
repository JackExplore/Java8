package com.mashibing.jvm.c2_classloader;

public class T005_LoadClassByHand {

    public static void main(String[] args) throws ClassNotFoundException {

        Class clazz = T005_LoadClassByHand.class.getClassLoader().loadClass("com.mashibing.jvm.c2_classloader.T002_ClassLoaderLevel");
        System.out.println(clazz.getName());
    }
}
