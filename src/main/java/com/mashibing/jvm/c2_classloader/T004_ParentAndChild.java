package com.mashibing.jvm.c2_classloader;

public class T004_ParentAndChild {

    public static void main(String[] args) {

        System.out.println(T004_ParentAndChild.class.getClassLoader());
        System.out.println(T004_ParentAndChild.class.getClassLoader().getClass().getClassLoader());
        System.out.println(T004_ParentAndChild.class.getClassLoader().getParent());
        System.out.println(T004_ParentAndChild.class.getClassLoader().getParent().getParent());
    }
    /**
     * out:
     * sun.misc.Launcher$AppClassLoader@18b4aac2
     * null
     * sun.misc.Launcher$ExtClassLoader@135fbaa4
     * null
     */
}
