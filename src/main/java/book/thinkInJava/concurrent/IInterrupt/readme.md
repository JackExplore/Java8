
interrupt() 可以打断被互斥所阻塞的调用。

注意，当你在线程上调用 interrupt() 时，
中断发生的唯一时刻是在任务要进入到阻塞操作中，或者已经在阻塞操作内部时。

简单 I/O 和 synchronized 上的调用是不可中断的。
你能够中断对 sleep() 的调用（或者任何要求抛出 InterruptException 的调用），
但是，你不能中断正在试图获取 synchronized 锁或者试图执行IO操作的线程。

#两种 interrupted() 退出方式
调用 interrupt() 中断程序执行，两种退出方式：

> 1、任务进入到阻塞操作中，或者已经在阻塞操作内部；

> 2、调用 interruptd()来检查中断状态，这不仅可以告诉你 interrupt() 是否被调用过，而且还可以清除中断状态；；

> 所以，你可以经由单一的 InterruptException 或单一的成功的 Thread.interrupted() 测试来得到这种通知。
状态位会清除，以确保并发结构不会就某个任务被中断这个问题通知你两次。


 