package book.thinkInJava.concurrent.CMoreFunction;

import java.util.concurrent.TimeUnit;

class ADaemon implements Runnable {

    @Override
    public void run() {
        System.out.println("Starting ADaemon");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("This should always run ?");
        }
    }
}

public class DaemonsDontRunFinally {

    public static void main(String[] args) {

        Thread t = new Thread(new ADaemon());
        t.setDaemon(true);  // 尝试注释掉这一句
        t.start();
    }
}
