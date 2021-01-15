package book.thinkInJava.concurrent.BExecutor;

import book.thinkInJava.concurrent.AThead.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {

    public static void main(String[] args) {

        // Constructor argument is number of threads
        // 可以看到多个任务执行完成后才由新任务再进入
        ExecutorService exec = Executors.newFixedThreadPool(2);

        for(int i=0; i<5; i++){
            exec.execute(new LiftOff(5));
        }

        exec.shutdown();
    }
}
