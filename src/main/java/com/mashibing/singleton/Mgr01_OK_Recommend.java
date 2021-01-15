package com.mashibing.singleton;

/**
 * 饿汉式
 *
 * 类加载到内存后，就实例化一个单例，JVM保证线程安全
 * 简单实用，推荐使用！@
 *
 * 唯一缺点，不管用到与否，类装载时候就完成实例化
 * Class.forName(""); // 话说，如果不用装载干嘛？
 */
public class Mgr01_OK_Recommend {

    // JVM 来做初始化，所以由JVM来保证单例
    private static final Mgr01_OK_Recommend INSTANCE = new Mgr01_OK_Recommend();

    private Mgr01_OK_Recommend(){
    }

    public static Mgr01_OK_Recommend getInstance(){
        return INSTANCE;
    }

    public void m(){
        System.out.println("m");
    }

    public static void main(String[] args) {
        Mgr01_OK_Recommend m1 = Mgr01_OK_Recommend.getInstance();
        Mgr01_OK_Recommend m2 = Mgr01_OK_Recommend.getInstance();
        System.out.println(m1 == m2);
    }
}
