package com.mashibing.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SocketMultiplexingSingleThreadv1 {

    private ServerSocketChannel server = null;
    private Selector selector = null;   // Linux 多路不用器 ( select pool epool kqueue)
    int port = 9090;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // select pool epoll / 如果在 epool 模型下， open() -> epool_create
            selector = Selector.open();

            /**
             * server 约等于 listen 状态的 fd4
             *
             * register
             * 如果：
             * select, poll : jvm 里开辟一个数组 fd4 放进去
             * epoll        : epoll_ctl(fd3, ADD, fd4, EPOLLIN?
             */
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("服务器启动了。。。。。");
        try {
            while (true) {
                Set<SelectionKey> keys = selector.keys();
                System.out.println("selector.keys().size = " + keys.size());

                // 1 调用多路复用器
                /**
                 * select() 是啥意思：
                 * 1、select/poll ： 其实，内核的 select(fd4)  poll(fd4)
                 * 2、epoll ： 其实，内核的 epoll_wait()
                 */
                while(selector.select(500) > 0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();  // 返回有状态的 fd 集合
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    // so, 管你什么多路复用器，你呀只管给我一个状态，我还得一个一个的去处理他们的 R/W。同步好辛苦！！！
                    // NIO 自己对着每一个 fd 调用系统调用，浪费资源，那么你看，这里是不是调用了一次 select 方法
                    while(iter.hasNext()){

                        SelectionKey next = iter.next();
                        iter.remove();  // 不移除会重复循环处理？
                        if(next.isAcceptable()){
                            /**
                             * 看代码的时候，这里是重点，如果要去接受一个新的连接
                             * 语义上，accept 接受连接且返回新连接的 fd 对吧？
                             * 那新的 fd 怎么办？
                             * select/poll, 因为他们内核没有空间，那么在 jvm 中保存和前边的 fd4 那个 listen 在一起
                             * epoll, 我们希望通过 epoll_ctl 把新的客户端 fd 注册到内核空间
                             */
                            acceptHandler(next);
                        }else if(next.isReadable()){
                            readHandler(next);
                            // 在当前线程，这个方法可能会阻塞，如果阻塞了 十年，其他的 IO 早就没电了。。。
                            
                        }


                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void acceptHandler(SelectionKey key) {

    }

    private void readHandler(SelectionKey key) {
        try{
            ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
            SocketChannel client = ssc.accept();    // 来啦，目的就是调用 accept 接受客户端 fd7
            client.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(8192);

            /**
             * select/poll, 因为他们内核没有空间，那么在 jvm 中保存和前边的 fd4 那个 listen 在一起
             * epoll, 我们希望通过 epoll_ctl 把新的客户端 fd 注册到内核空间
             */
            client.register(selector, SelectionKey.OP_READ, buffer);

            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
