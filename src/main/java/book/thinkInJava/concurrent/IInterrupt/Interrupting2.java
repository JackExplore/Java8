package book.thinkInJava.concurrent.IInterrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock -> 调用后一直阻塞到获得锁
 * <p>
 * tryLock -> 尝试是否能获得锁 会立即返回获取结果
 * <p>
 * lockInterruptibly -> 调用后一直阻塞到获得锁 但是接受中断信号(题主用过Thread#sleep吧)
 */
public class Interrupting2 {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(new Blocked2());
        t.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Issuing t.interrupt()");
        t.interrupt();



        //-----------------------------------
        /**
         * 在  wait/notify/notifyAll 前，必须先获得对象的锁，否则：
         * java.lang.IllegalMonitorStateException
         * Thinking in Java 4 P703/4
         */
//        Thread.currentThread().wait();
    }
}


class BlockedMutex {
    private Lock lock = new ReentrantLock();

    public BlockedMutex() {
        lock.lock();
    }

    /**
     * 当线程在运行过程中时，interrupt 无效
     */
    public void f() {
//        try {
//            lock.lockInterruptibly();
//        } catch (InterruptedException e) {
//            System.out.println("Interrupted from lock acquisition in f()");
//        }
        while(true){

        }
    }
}

class Blocked2 implements Runnable {
    BlockedMutex blockedMutex = new BlockedMutex();
    @Override
    public void run() {
        System.out.println("Waiting for f() in BlockedMutex");
        blockedMutex.f();
        System.out.println("Broken out of blocked call");
    }
}