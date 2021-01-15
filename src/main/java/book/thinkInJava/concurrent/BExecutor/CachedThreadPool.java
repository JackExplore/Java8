package book.thinkInJava.concurrent.BExecutor;

import book.thinkInJava.concurrent.AThead.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {

    public static void main(String[] args) {

        ExecutorService exec = Executors.newCachedThreadPool();

        for(int i=0; i<5; i++){
            exec.execute(new LiftOff(3));
        }

        /**
         * 对 shutdown() 方法的调用可以防止新任务被提交给这个 Executor ， 当前线程将继续运行在 shutdown() 被调用之前提交的所有任务。
         * 这个程序将在 Executor 中的所有任务完成之后尽快退出。
         */
        exec.shutdown();
    }
}
