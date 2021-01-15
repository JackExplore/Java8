package com.mashibing.juc.c004;

public class T {

    private static int count = 10;

    public synchronized static void m(){
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm(){    // 思考此时 m/mm 和 mmm 可以同时访问吗！可以
        synchronized (T.class){
            count--;
        }
    }

    public synchronized void mmm(){
        System.out.println("obj " + count--);
    }

    /**
     * 对比分别加锁 对象 和 class 能不能同时访问
     * 能
     * @param args
     */
    public static void main(String[] args) {
        new A().start();
        new B(new T()).start();
    }

    // 用于测试 class syn
    static class A extends Thread{

        @Override
        public void run() {
            while(T.count > 0){
                T.m();
            }
        }
    }
    // 用于测试 obj syn
    static class B extends Thread{
        T tb;
        B(T t){
            tb = t;
        }
        @Override
        public void run() {
            while(T.count > 0)
                tb.mmm();
        }
    }

}
