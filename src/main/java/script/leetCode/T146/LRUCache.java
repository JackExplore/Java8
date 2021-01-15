package script.leetCode.T146;

import java.util.HashMap;
import java.util.Iterator;

/**
 * LRU - LinkedHashMap
 *
 * 双向链表的操作，注意前后结点的连接关系！
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
            // 断开前后连接
            node.pre.next = node.next;
            node.next.pre = node.pre;
            // 放到最后面
            node.pre = tail.pre;
            node.next = tail;
            tail.pre.next = node;       // ! 这一步关键
            tail.pre = node;
            // 最近使用
            return node.data;
        }
        return -1;
    }

    public void put(int key, int value) {
        Node node = hashMap.get(key);
        // 如果存在
        if (node != null) {
            // 断开前后连接
            node.pre.next = node.next;
            node.next.pre = node.pre;
            // 放到最后面
            node.pre = tail.pre;
            node.next = tail;
            tail.pre.next = node;       // ! Key Step
            tail.pre = node;
            // 最近使用
            node.data = value;
            return;
        }

        // 如果不存在

        // 1 长度是否超限
        while (size >= capacity) {
            Node del = head.next;
            head.next = head.next.next;
            head.next.pre = head;

            hashMap.remove(del.key);
            size--;
        }
        // 2 如果是新结点
        node = new Node();
        node.key = key;
        node.data = value;

        // 注意这一步操作
        node.next = tail;
        node.pre = tail.pre;
        tail.pre.next = node;   // ! IMPORTANT
        tail.pre = node;

        hashMap.put(key, node);
        size++;
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        printHash(lRUCache.hashMap);

        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        printHash(lRUCache.hashMap);

        lRUCache.get(1);    // 返回 1
        printHash(lRUCache.hashMap);

        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        printHash(lRUCache.hashMap);

        lRUCache.get(2);    // 返回 -1 (未找到)
        printHash(lRUCache.hashMap);

        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        printHash(lRUCache.hashMap);

        lRUCache.get(1);    // 返回 -1 (未找到)
        printHash(lRUCache.hashMap);

        lRUCache.get(3);    // 返回 3
        printHash(lRUCache.hashMap);

        lRUCache.get(4);    // 返回 4
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
