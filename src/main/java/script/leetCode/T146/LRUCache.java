package script.leetCode.T146;

import java.util.HashMap;
import java.util.Iterator;

/**
 * LRU - LinkedHashMap
 *
 * ˫������Ĳ�����ע��ǰ��������ӹ�ϵ��
 *
 * leetCode 146
 */
public class LRUCache {

    HashMap<Integer, Node> hashMap;
    int size;
    int capacity;
    Node head;
    Node tail;

    private class Node {
        Integer key;
        Integer data;
        Node pre;
        Node next;

        @Override
        public String toString() {
            return "[" + key + ", " + data + "]";
        }
    }

    public LRUCache(int capacity) {
        hashMap = new HashMap<>(capacity);
        this.capacity = capacity;
        size = 0;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        Node node = hashMap.get(key);
        if (node != null) {
            // �Ͽ�ǰ������
            node.pre.next = node.next;
            node.next.pre = node.pre;
            // �ŵ������
            node.pre = tail.pre;
            node.next = tail;
            tail.pre.next = node;       // ! ��һ���ؼ�
            tail.pre = node;
            // ���ʹ��
            return node.data;
        }
        return -1;
    }

    public void put(int key, int value) {
        Node node = hashMap.get(key);
        // �������
        if (node != null) {
            // �Ͽ�ǰ������
            node.pre.next = node.next;
            node.next.pre = node.pre;
            // �ŵ������
            node.pre = tail.pre;
            node.next = tail;
            tail.pre.next = node;       // ! Key Step
            tail.pre = node;
            // ���ʹ��
            node.data = value;
            return;
        }

        // ���������

        // 1 �����Ƿ���
        while (size >= capacity) {
            Node del = head.next;
            head.next = head.next.next;
            head.next.pre = head;

            hashMap.remove(del.key);
            size--;
        }
        // 2 ������½��
        node = new Node();
        node.key = key;
        node.data = value;

        // ע����һ������
        node.next = tail;
        node.pre = tail.pre;
        tail.pre.next = node;   // ! IMPORTANT
        tail.pre = node;

        hashMap.put(key, node);
        size++;
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // ������ {1=1}
        printHash(lRUCache.hashMap);

        lRUCache.put(2, 2); // ������ {1=1, 2=2}
        printHash(lRUCache.hashMap);

        lRUCache.get(1);    // ���� 1
        printHash(lRUCache.hashMap);

        lRUCache.put(3, 3); // �ò�����ʹ�ùؼ��� 2 ���ϣ������� {1=1, 3=3}
        printHash(lRUCache.hashMap);

        lRUCache.get(2);    // ���� -1 (δ�ҵ�)
        printHash(lRUCache.hashMap);

        lRUCache.put(4, 4); // �ò�����ʹ�ùؼ��� 1 ���ϣ������� {4=4, 3=3}
        printHash(lRUCache.hashMap);

        lRUCache.get(1);    // ���� -1 (δ�ҵ�)
        printHash(lRUCache.hashMap);

        lRUCache.get(3);    // ���� 3
        printHash(lRUCache.hashMap);

        lRUCache.get(4);    // ���� 4
        printHash(lRUCache.hashMap);
    }

    public static void printHash(HashMap hashMap){
        Iterator iterator = hashMap.values().iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
        System.out.println("======================");
    }
}
