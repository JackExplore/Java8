package com.mashibing.juc.c026_ThreadPool;

import java.util.concurrent.Executor;

/**
 * Executor：线程的 定义 和 运行 分开的含义
 */
public class T01_MyExecutor implements Executor {

    public static void main(String[] args) {
        new T01_MyExecutor().execute(()-> System.out.println("command.run()"));
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }

}
