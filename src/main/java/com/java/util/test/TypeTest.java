package com.java.util.test;

import java.lang.reflect.Type;

public class TypeTest {
    public static void main(String[] args) {
        Type t = TypeTest.class;
        System.out.println(t);
        System.out.println("t.getTypeName() = " + t.getTypeName());
        System.out.println("t.getClass()" + t.getClass());
        System.out.println(TypeTest.class);
        System.out.println(Class.class);
        System.out.println("String.class = " + String.class);
    }
}
