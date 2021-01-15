package com.mashibing.singleton;

import java.util.concurrent.TimeUnit;

/**
 * 懒汉式
 * 未解决效率问题，但这种写法是不对的
 */
public class Mgr05 {
    private static Mgr05 INSTANCE;

    private Mgr05(){}

    public static Mgr05 getInstance(){
        if(INSTANCE == null){
            synchronized (Mgr05.class){
                try{
                    TimeUnit.MILLISECONDS.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                INSTANCE = new Mgr05();
            }
        }
        return INSTANCE;
    }

    public void m(){
        System.out.println("m");
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++){
            new Thread(()->
                    System.out.println(Mgr05.getInstance().hashCode())
            ).start();
        }
    }
}
