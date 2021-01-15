package book.thinkInJava.concurrent;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * > 用并发解决的问题大体上可以分为【速度】和【设计可管理性】两种；
 * > 事实上，从性能的角度看，如果没有任务会阻塞，那么在单处理器机器上使用并发就没有任何意义；
 * > 编写多线程最基本的困难：在于协调不同线程驱动的任务之间对共享资源的使用，以使得这些资源不会同时被多个任务访问；
 * > 通过使用多线程机制，这些独立任务（也被称为子任务）中的每一个都将由执行线程来驱动。
 *   一个线程就是在进程中的一个单一的顺序控制流。
 *
 * > P655 下方：如果多个线程在创建LiftOff线程，那么就有可能会有多个 LiftOff 拥有相同的 id，本章稍后了解为什么？
 *
 * > 在使用普通对象时，这对于垃圾回收来说是一场公平的游戏，但是在使用 Thread 时，情况就不同了。
 *  每个 Thread 都 “注册” 了它自己，因此确实有一个对它的引用，而且在它的任务退出其 run() 并死亡之前，垃圾回收器都无法清除它。
 *
 * > 非常常见的情况是，单个的 Executor 被用来创建和管理系统中所有的任务。
 *
 * > 注意，在任何线程池中，现有线程在可能的情况下，都会被自动复用。
 *
 * > 对 sleep() 的调用可以抛出 InterruptedException 异常，它在 run() 中被捕获。
 *      因为异常不能跨线程传播回 main()，所以你必须在本地处理所有在任务内部产生的异常。
 *
 * > Thread.currentThread() 来获得对驱动该任务的 Thread 对象的引用。
 * > 后台线程在不执行 finally 子句的情况下就会终止其 run() 方法。
 */

/**
 * > 要执行的任务和驱动它的线程之间有一个差异，因为你对 Thread 类实际没有任何控制权：
 *      在 Java 中，Thread 类自身不执行任何操作，它只是驱动赋予它的任务
 * > isInterrupted() 然而，异常被捕获时将清理这个标志，所以在 catch 子句中，在异常被捕获的时候这个标志总是为假。！！！！
 *
 * > 所有对象都自动含有单一的锁（也称为监视器）
 *
 * > 一个任务可以多次获得对象的锁，JVM负责跟踪对象被枷锁的次数；
 *
 * > 针对每个类，也有一个锁（作为类的 Class 对象的一部分），所以 synchronized static 方法可以在类的范围内防止对 static 数据的并发访问。
 *
 * > 显示的Lock对象在枷锁和释放锁方面，相对于内建的synchronized锁来说，还赋予了你更细粒度的控制力。
 *
 * > 什么才属于原子操作呢？ 对域中的值做赋值操作通常都是原子性的。
 *
 * > 尽管 try-finally 所需的代码比 synchronized 关键字要多，但是这也代表了显示的 Lock 对象的优点之一，
 *      如果在使用 synchronized 关键字时，某些事物失败了，那么就会抛出一个异常。但是你没有机会去做任何清理工作，以维护系统使其处于良好状态。
 *      有了显式的 Lock 对象，你就可以使用 finally 子句将系统维护在正确的状态了。
 * >
 */
public class Main {


    /**
     * 多线程 / 高并发
     *
     * JVM原理 / 垃圾收集机制
     *
     * 数据库：MySQL索引优化，查询优化，Redis数据库，使用场景及限制
     *
     * 算法：红黑树，数据结构
     *
     * NIO
     *
     * 集合：HashMap / ConcurrentHashMap
     *
     * 框架：Spring，微服务
     *
     * xUnix开发
     */

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }
}



/**
 *
 * 20200912 多线程书本看完
 * 然后开始看视频
 *
 */