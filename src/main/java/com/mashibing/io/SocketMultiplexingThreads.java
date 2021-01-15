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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketMultiplexingThreads {

    private ServerSocketChannel server = null;
    private Selector selector1 = null;
    private Selector selector2 = null;
    private Selector selector3 = null;
    int port = 9090;

    public void initServer(){
        try{
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            selector1 = Selector.open();
            selector2 = Selector.open();
            selector3 = Selector.open();

            server.register(selector1, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        SocketMultiplexingThreads service = new SocketMultiplexingThreads();
        service.initServer();
        NioThread T1 = new NioThread(service.selector1, 2);
        NioThread T2 = new NioThread(service.selector2);
        NioThread T3 = new NioThread(service.selector3);

        T1.start();
        TimeUnit.SECONDS.sleep(1);

        T2.start();
        T3.start();

        System.out.println("服务器启动了.......");

        System.in.read();
    }
}

class NioThread extends Thread{

    Selector selector = null;

    static int selectors = 0;

    int id = 0;

    volatile static BlockingQueue<SocketChannel>[] queue;

    static AtomicInteger idx = new AtomicInteger();

    NioThread(Selector sel, int n){
        this.selector = sel;
        this.selectors = n;

        /**
         * 相当于创建了几个阻塞队列存放 SocketChannel
         */
        queue = new LinkedBlockingQueue[selectors];
        for (int i = 0; i < n; i++) {
            queue[i] = new LinkedBlockingQueue<>();
        }
        System.out.println("Boss 启动。。。");
    }

    NioThread(Selector sel){
        this.selector = sel;
        id = idx.getAndIncrement() % selectors;
        System.out.println("worker : " + id + " 启动！");
    }

    @Override
    public void run(){
        try{
            while (true) {
                while(selector.select(10) > 0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while(iter.hasNext()){
                        SelectionKey key = iter.next();
                        iter.remove();
                        if(key.isAcceptable()){
                            acceptHandler(key);
                        }else if(key.isReadable()){
                            readHandler(key);
                        }
                    }
                }

                if(!queue[id].isEmpty()){
                    ByteBuffer buffer = ByteBuffer.allocate(8192);
                    SocketChannel client = queue[id].take();
                    client.register(selector, SelectionKey.OP_READ, buffer);
                    System.out.println("-------------------------------------------");
                    System.out.println("新客户端：" + client.getRemoteAddress() + client.socket().getPort() + " 分配到：" + (id));
                    System.out.println("-------------------------------------------");
                }
            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    private void acceptHandler(SelectionKey key) {
        try{
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept();
            client.configureBlocking(false);
            int num = idx.getAndIncrement() % selectors;

            queue[num].add(client);     // 让另外的线程知道客户端的存在
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readHandler(SelectionKey key) {
        try{
            ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
            SocketChannel client = ssc.accept();    // 来啦，目的就是调用 accept 接受客户端 fd7
            client.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(8192);

            client.register(selector, SelectionKey.OP_READ, buffer);

            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
