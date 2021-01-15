package book.thinkInJava.concurrent.DException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ExceptionThread2 implements Runnable{

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("4 run() by " + t);
        System.out.println("5 eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("[uncaughtException thread : ] " + t + " [caught] " + e);
    }
}

class HandlerThreadFactory implements ThreadFactory{

    private static int count = 10;
    @Override
    public Thread newThread(Runnable r) {
        System.out.println();
        System.out.println("0 " + Thread.currentThread());
        System.out.println("1 " + this.toString().substring(this.toString().lastIndexOf(".")+1) + " [creating new Thread] " + r.toString().substring(this.toString().lastIndexOf(".") + 1));
        Thread t = new Thread(r, "【" + count++ + "】");
        System.out.println("2 created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("3 eh = " + t.getUncaughtExceptionHandler());
        return t;
    }
}

public class CaptureUncaughtException {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread());
//        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());     // 为什么会出现两次 0/1/2/3

        ExecutorService exec = Executors.newCachedThreadPool();
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        exec.execute(new ExceptionThread2());
    }
}
