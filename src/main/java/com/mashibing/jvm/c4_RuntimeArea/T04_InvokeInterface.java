package com.mashibing.jvm.c4_RuntimeArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Invoke Interface
 */
public class T04_InvokeInterface {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("hello");

        System.out.println("hello");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("Hello");
    }
}
