package com.mashibing.juc.c006;

public class T implements Runnable{

    private int count = 100;

    @Override
    public synchronized void run() {

        count--;
        System.out.println(Thread.currentThread().getName() + "\t count = " + count);

    }

    public static void main(String[] args) {
        T t = new T();
        for(int i=0; i<100; i++){
            new Thread(t, "THREAD - " + i).start();
        }
    }
}