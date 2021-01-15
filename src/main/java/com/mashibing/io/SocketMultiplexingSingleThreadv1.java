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
    private Selector selector = null;   // Linux ��·������ ( select pool epool kqueue)
    int port = 9090;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // select pool epoll / ����� epool ģ���£� open() -> epool_create
            selector = Selector.open();

            /**
             * server Լ���� listen ״̬�� fd4
             *
             * register
             * �����
             * select, poll : jvm �￪��һ������ fd4 �Ž�ȥ
             * epoll        : epoll_ctl(fd3, ADD, fd4, EPOLLIN?
             */
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("�����������ˡ���������");
        try {
            while (true) {
                Set<SelectionKey> keys = selector.keys();
                System.out.println("selector.keys().size = " + keys.size());

                // 1 ���ö�·������
                /**
                 * select() ��ɶ��˼��
                 * 1��select/poll �� ��ʵ���ں˵� select(fd4)  poll(fd4)
                 * 2��epoll �� ��ʵ���ں˵� epoll_wait()
                 */
                while(selector.select(500) > 0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();  // ������״̬�� fd ����
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    // so, ����ʲô��·����������ѽֻ�ܸ���һ��״̬���һ���һ��һ����ȥ�������ǵ� R/W��ͬ�������࣡����
                    // NIO �Լ�����ÿһ�� fd ����ϵͳ���ã��˷���Դ����ô�㿴�������ǲ��ǵ�����һ�� select ����
                    while(iter.hasNext()){

                        SelectionKey next = iter.next();
                        iter.remove();  // ���Ƴ����ظ�ѭ������
                        if(next.isAcceptable()){
                            /**
                             * �������ʱ���������ص㣬���Ҫȥ����һ���µ�����
                             * �����ϣ�accept ���������ҷ��������ӵ� fd �԰ɣ�
                             * ���µ� fd ��ô�죿
                             * select/poll, ��Ϊ�����ں�û�пռ䣬��ô�� jvm �б����ǰ�ߵ� fd4 �Ǹ� listen ��һ��
                             * epoll, ����ϣ��ͨ�� epoll_ctl ���µĿͻ��� fd ע�ᵽ�ں˿ռ�
                             */
                            acceptHandler(next);
                        }else if(next.isReadable()){
                            readHandler(next);
                            // �ڵ�ǰ�̣߳�����������ܻ���������������� ʮ�꣬������ IO ���û���ˡ�����
                            
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
            SocketChannel client = ssc.accept();    // ������Ŀ�ľ��ǵ��� accept ���ܿͻ��� fd7
            client.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(8192);

            /**
             * select/poll, ��Ϊ�����ں�û�пռ䣬��ô�� jvm �б����ǰ�ߵ� fd4 �Ǹ� listen ��һ��
             * epoll, ����ϣ��ͨ�� epoll_ctl ���µĿͻ��� fd ע�ᵽ�ں˿ռ�
             */
            client.register(selector, SelectionKey.OP_READ, buffer);

            System.out.println("-------------------------------------------");
            System.out.println("�¿ͻ��ˣ�" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
