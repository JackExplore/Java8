package com.mashibing.juc.c021_VarHandle;

import java.lang.invoke.MethodHandles;
//import java.lang.invoke.VarHandle;

/**
 * 意义：通过 Handle 直接操作里面的值，为了 CAS 操作 !
 * 除了能够完成普通操作，还能够完成原子性的普通操作，这就是 varHandle 的含义
 *
 * 1、普通属性原子操作；
 * 2、比起反射更高效，直接操作二进制码
 *
 * since JDK 1.9
 */
public class T01_HelloVarHandle {

/*    int x = 8;
    private static VarHandle handle;

    static {
        try {
            handle = MethodHandles.lookup().findVarHandle(T01_HelloVarHandle.class, "x", int.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        T01_HelloVarHandle t = new T01_HelloVarHandle();

        // 通过 handle 设值
        System.out.println(handle.get(t));
        handle.set(t, 9);
        System.out.println(t.x);

        // CAS Oper
        handle.compareAndSet(t, 9, 10);
        System.out.println(t.x);

        //
        handle.getAndAdd(t, 10);
        System.out.println(t.x);
    }*/
}
