package book.thinkInJava.concurrent.IInterrupt;

import java.util.concurrent.TimeUnit;

/**
 * 两种退出方式：
 *
 * 1/ 如果 interrupt() 在注释 point2 之后（即在非阻塞的操作过程中调用），
 *      那么首先循环将结束，然后所有的本地对象将被销毁，最后经由while顶部退出；                  @状态位检测
 * 2/ 但是，如果 interrupt() 在 point1 和 point2 之间（ while() 检查后，阻塞 sleep() 之前，
 *      那么这个任务就会在第一次试图调用阻塞操作之前，经由 InterruptException 退出。            @InterruptException
 *
 * 作者云：
 * 被设计用来响应 interrupt() 的类必须建立一种策略，来确保它将保持一致的状态。
 * 这通常意味着所有需要清理的对象创建操作的后面，都必须紧跟 try-finally 子句，从而使得无论 run() 循环如何退出，清理都会发生。
 * 像这样的代码会工作得很好。
 *
 */
class NeedsCleanup {
    private final int id;

    public NeedsCleanup(int ident) {
        id = ident;
        System.out.println("NeedsCleanup " + id);
    }

    public void cleanup() {
        System.out.println("Cleaning up " + id);
    }
}

class Blocked3 implements Runnable {

    private volatile double d = 0.0;

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // point 1
                NeedsCleanup n1 = new NeedsCleanup(1);

                // start try-finally
                Thread.currentThread().interrupt();     // 设置了中断状态位     test 1 condition

                try {
                    System.out.println("Sleeping");
                    TimeUnit.MILLISECONDS.sleep(50);

                    System.out.println("is here arrive");   // test 1   condition

                    // point 2
                    NeedsCleanup n2 = new NeedsCleanup(2);
                    try {
                        System.out.println("Calculating");
                        // operation
                        for (int i = 1; i < 2500000; i++) {
                            d = d + (Math.PI + Math.E) / d;
                        }
                        System.out.println("Finished time-consuming operation");
                    }finally {
                        n2.cleanup();
                    }
                }finally {
                    n1.cleanup();
                }
            }
            System.out.println("Exiting via while() test");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}


public class InterruptingIdiom {

    public static void main(String[] args) throws InterruptedException {
//        if(args.length != 1){
//            System.out.println("usage: java InterruptingIdiom delay-in-mS");
//            System.exit(1);
//        }
        Thread t = new Thread(new Blocked3());
        t.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Send interrupt...");
        t.interrupt();
    }
}
