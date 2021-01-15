package com.mashibing.jvm.c3_jmm;

import com.mashibing.jvm.agent.ObjectSizeAgent;

public class T03_SizeOfAnObject {
    public static void main(String[] args) {
        System.out.println(ObjectSizeAgent.sizeOf(new Object()));
        System.out.println(ObjectSizeAgent.sizeOf(new int[] {}));
        System.out.println(ObjectSizeAgent.sizeOf(new P()));
        /**
         * Result A：
         * 16 - new Object()
         *      8   markword
         *      4   classpointer(-XX:+UseCompressedClassPointers 打开了指针压缩，否则为 8 )
         *      4   padding 对齐
         * 16 - new int[]
         *      8   markword
         *      4   classpointer    / this is not oops!!!!
         *      4   length      数组长度
         * 32
         *
         * Result B：
         * -XX:+UseCompressedClassPointers
         * 16
         * 24
         * 40
         *
         */
    }

    //一个Object占多少个字节
    // -XX:+UseCompressedClassPointers -XX:+UseCompressedOops
    // Oops = ordinary object pointers  普通对象的指针，这是两个不同的指针参数
    private static class P {
                        //8 _markword
                        //4 _class pointer
        int id;         //4
        String name;    //4
        int age;        //4

        byte b1;        //1
        byte b2;        //1

        Object o;       //4
        byte b3;        //1

    }
}
