package book.thinkInJava.concurrent.DException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingDefaultHandler {

    public static void main(String[] args) {

        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        ExecutorService exec = Executors.newCachedThreadPool();

        exec.execute(new ExceptionThread());

        // 为什么会很久才结束？
    }
}
