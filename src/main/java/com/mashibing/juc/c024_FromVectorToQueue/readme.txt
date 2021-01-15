
容器的演化过程


同步容器类

1、 Vector/(List) Hashtable/(HashMap)  : 前者为早期使用 synchronized 实现，()后者为非同步实现

2、 ArrayList HashSet : 未考虑多线程安全(未实现同步)

3、 HashSet / Hashtable      StringBuilder / StringBuffer

4、 Collections.synchronizedXXXXX 工厂方法使用的也是 synchronized

使用早期的同步容器的不足之处:
http://blog.csdn.net/itm_hadf/article/details/7506529

使用新的并发容器：
http://xuganggogo.iteye.com/blog/321630
