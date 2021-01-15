package com.java.util.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {

    public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException {


        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe)theUnsafe.get(null);

        // 1 ��������ʵ��
        Author author = (Author) unsafe.allocateInstance(Author.class);

        // 2 �������������
        Field ageField = Author.class.getDeclaredField("age");
        long fieldOffset = unsafe.objectFieldOffset(ageField);
        System.out.println(fieldOffset);

        // 3 ��������


    }
}
