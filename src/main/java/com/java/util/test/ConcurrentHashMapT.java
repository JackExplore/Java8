package com.java.util.test;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ConcurrentHashMapT<K, V> /*extends AbstractMap<K,V> implements ConcurrentMap<K,V>, Serializable */ {


    /*
     * Overview:
     * 主要目标 & 次要目标
     * The primary design goal of this hash table is to maintain concurrent readability
     * (typically method get(), but also iterators and related methods) while minimizing update contention.
     * Secondary goals are to keep space consumption about the same or better than java.util.HashMap,
     * and to support high initial insertion rates on an empty table by many threads. !!!
     *
     * The table is lazily initialized to a power-of-two size upon the first insertion.
     *
     *
     *
     */

    /* ---------------- Constants -------------- */
    /**
     * The largest possible table capacity.
     * The two bits of 32bit hash fields are used for control purposes.
     */
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private static final int DEFAULT_CAPACITY = 16;

    static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8; // ?




    public static void main(String[] args) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

        concurrentHashMap.put("a", "dsafas");

        HashMap hashMap = new HashMap();

        System.out.println(1 << 30);
        System.out.println(1 << 31);
        System.out.println(1 << 32);


    }


}
