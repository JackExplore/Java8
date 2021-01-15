package com.mashibing.jvm.c2_classloader;

public class T010_Parent {

    private static T006_MSBClassLoader parent = new T006_MSBClassLoader();

    private static class MyLoader extends ClassLoader{
        public MyLoader(){
            super(parent);
        }
    }

    public static void main(String[] args) {
        System.out.println(MyLoader.getSystemClassLoader().getParent());
        System.out.println(MyLoader.class.getClassLoader());
        System.out.println(MyLoader.class.getClassLoader().getParent());
        System.out.println(MyLoader.class.getClassLoader().getParent().getParent());
        System.out.println(MyLoader.class.getClassLoader().getClass().getClassLoader());
    }
}