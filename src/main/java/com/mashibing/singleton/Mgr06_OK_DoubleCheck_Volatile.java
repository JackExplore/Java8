package com.mashibing.singleton;

/**
 * 懒汉式
 * 双重检查 + volatile
 */
public class Mgr06_OK_DoubleCheck_Volatile {

    // 不加 volatile 也很难出现问题，但问题会出现在 指令重排序上！ 极难出现
    private static volatile Mgr06_OK_DoubleCheck_Volatile INSTANCE;

    private Mgr06_OK_DoubleCheck_Volatile(){}

    // 双重检查 之 volatile
    public static Mgr06_OK_DoubleCheck_Volatile getInstance(){
        // 省略业务逻辑
        if(INSTANCE == null){
            synchronized (Mgr06_OK_DoubleCheck_Volatile.class){
                if(INSTANCE == null){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new Mgr06_OK_DoubleCheck_Volatile(); // 指令重排序会进行优化
                    /**
                     * new 对象的三个步骤：
                     * 1 给对象申请内存 default value
                     * 2 初始化空间
                     * 3 赋值到变量 INSTANCE
                     */
                }
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
                    System.out.println(Mgr06_OK_DoubleCheck_Volatile.getInstance().hashCode())
            ).start();
        }
    }
}
