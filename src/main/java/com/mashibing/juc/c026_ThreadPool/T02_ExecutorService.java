package com.mashibing.juc.c026_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T02_ExecutorService {

    public static void main(String[] args) {

        ExecutorService e = Executors.newCachedThreadPool();

    }
}
