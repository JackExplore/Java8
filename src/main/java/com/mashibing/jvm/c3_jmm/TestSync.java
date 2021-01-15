package com.mashibing.jvm.c3_jmm;

/**
 * synchronized
 * 方法层面：加sync标志
 * 同步块：monitor enter/exit
 *
 * 1. 字节码层面
 *    ACC_SYNCHRONIZED
 *    monitorenter monitorexit
 * 2. JVM层面
 *    C C++ 调用了操作系统提供的同步机制
 * 3. OS和硬件层面
 *    X86 : lock cmpxchg / xxx
 *    [https](https://blog.csdn.net/21aspnet/article/details/88571740)[://blog.csdn.net/21aspnet/article/details/](https://blog.csdn.net/21aspnet/article/details/88571740)[88571740](https://blog.csdn.net/21aspnet/article/details/88571740)
 */
public class TestSync {

    // see bytecode classlib
    synchronized void m() {

    }

    void n(){
        synchronized (this){
            System.out.println();
        }
    }

    public static void main(String[] args) {

    }
}
