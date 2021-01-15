package com.mashibing.singleton;

import java.util.concurrent.TimeUnit;

/**
 * 懒汉式
 * 安全问题
 */
public class Mgr03 {

    private static Mgr03 INSTANCE;

    /**
     * 解决的懒汉式问题，却带来安全性问题
     * @return
     */
    public static Mgr03 getInstance(){
        if(INSTANCE == null){
            // 造成不一致
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new Mgr03();
        }
        return INSTANCE;
    }

    public void m(){
        System.out.println("m");
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++){
            new Thread(()->
                    System.out.println(Mgr03.getInstance().hashCode())
            ).start();
        }
    }
}
