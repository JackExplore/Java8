package com.mashibing.singleton;

import java.util.concurrent.TimeUnit;

/**
 * 懒汉式
 * 效率问题
 */
public class Mgr04 {

    private static Mgr04 INSTANCE;

    // 为解决 03 问题，加锁来解决，却带来效率上的问题
    public static synchronized Mgr04 getInstance(){
        if(INSTANCE == null){
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new Mgr04();
        }
        return INSTANCE;
    }

    public void m(){
        System.out.println("m");
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++){
            new Thread(()->
                    System.out.println(Mgr04.getInstance().hashCode())
            ).start();
        }
    }
}
