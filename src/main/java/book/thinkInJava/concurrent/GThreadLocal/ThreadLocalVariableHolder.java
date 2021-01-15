package book.thinkInJava.concurrent.GThreadLocal;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 防止任务在共享资源上产生冲突的第二种方式是根除对变量的共享，
 * <p>
 * 线程本地存储是一种自动化机制，可以为使用相同变量的每个不同的线程都创建不同的存储。
 * <p>
 * 主要是，它们使得你可以将状态与线程关联起来。
 */

class Accessor implements Runnable {
    private final int id;
    public Accessor(int idn){
        this.id = idn;
    }
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }
    public String toString(){
        return "#" + id + " : " + ThreadLocalVariableHolder.get();
    }
}

public class ThreadLocalVariableHolder {

    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
        private Random rand = new Random(47);
        protected synchronized Integer initialValue(){
            return rand.nextInt(10000);
        }
    };

    /**
     * 注意，increment() 和 get() 方法都不是 synchronized 的，
     * 因为 ThreadLocal 保证不会出现竞争条件。
     *
     * 可以参看 ThreadLocal 源码
     */

    // 以当前线程对象为 key -> ThreadLocal
    public static void increment(){
        value.set(value.get() + 1);
    }
    // 所以，get方法将返回与其线程相关联的对象的副本
    public static int get(){
        return value.get();
    }

    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i=0; i<5; i++){
            exec.execute(new Accessor(i));
        }
        TimeUnit.SECONDS.sleep(1);
        exec.shutdownNow();
    }

}
