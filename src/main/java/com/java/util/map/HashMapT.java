package com.java.util.map;

import javax.swing.tree.TreeNode;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Permits <tt>null</tt> values and the <tt>null</tt> key.
 * ���� null ��ֵ��
 * <p>
 * The <tt>HashMap</tt> class is roughly equivalent to <tt>Hashtable</tt>, except that it is unsynchronized and permits nulls.
 * ���˷�ͬ�����ƺ������ֵ��HashMap �� Hashtable ����һ��
 * <p>
 * Iteration over collection views requires time proportional to the "capacity" of the <tt>HashMap</tt> instance (the number of buckets) plus its size (the number
 * of key-value mappings).Thus, it's very important not to set the initial capacity too high (or the load factor too low) if iteration performance is important.
 * ������Ч�ʺ�Ͱ�������ͼ�ֵ�Գ��ȳɱ��������ԣ��������Ч�ʺ���Ҫ����ô����ʼ������Ҫ̫�ߣ��������Ӳ�Ҫ̫�͡�
 * <p>
 * <p>
 * An instance of HashMap has two parameters that affect its performance: initial capacity & load factor.
 * The capacity is the number of buckets in the hash table, and the initial capacity is simply the capacity at the time the hash table is created.
 * - ������ָͰ������
 * The load factor is a measure of how full the hash table is allowed to get before its capacity is automatically invreased.
 * - ���������Ƕ���һ����ϣ���ڴﵽ����ʱ�����Զ�����
 * When the number of entries in the hash table exceeds(chao guo) the product of the load factor and the current capacity,
 * the hash table is rehashed (that is, internal data structures are rebuilt)
 * so that the hash table has approximately twice the number of buckets.
 * - �����ֵ���������� �������Ӻ����� �Ľ�����������¹�ϣ��Ȼ�󣬱��������������������
 * ? ������ָ�������Ӻ�Ͱ��������
 * <p>
 * <p>
 *
 * As a general rule, the default load factor 0.75 offers a good tradeoff between time and space costs.
 *
 * If many mappings are to be stored in a HashMap instance, creating it with a sufficiently large capacity will
 * allow the mappings to be stored more efficiently than letting it perform automatic rehashing as needed to grow the table.
 *
 * ���� HashMap ��ͬ��������
 * Note that this implementation is not synchronized.
 * If... the map should be "wrapped" using the Collections.synchronizedMap method.
 * This is best done at creation time, to prevent accidental unsynchronized access to the map.
 * <pre>
 *     Map m = Collections.synchronizedMap(new HashMap(...));
 * </pre>
 *
 * The iterators ����Ϊ�ṹ�仯���¿���ʧ�ܣ�����ͨ��������Ĳ���������
 * Note that the fail-fast behavior of an iterator cannot be guaranteed as it is.
 * Generally speaking, impossible to make any hard guarantees in the presence of unsynchronized concurrent modification.
 * ע�⣬��������fail-fast��Ϊ���ܵõ���֤����Ϊһ����˵���ڴ��ڲ�ͬ���Ĳ����޸�ʱ�������������κ�Ӳ�Ա�֤��
 * fail-fast ֻ�Ǿ����Ŭ���׳� ConcurrentModificationException. ����Ϊ�����ڼ�� bug.
 *
 *
 *
 * This class is a member of the Java Collections Framework.
 * ? yes
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class HashMapT<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {

    /**
     * Implementation notes.
     *
     * However, since the vast majority of bins in normal use are not overpopulated,
     * checking for existence of tree bins may be delayed in the course of table methods.
     *
     *
     * Tree bins (i.e., bins whose elements are all TreeNodes) are ordered primarily by hashcode,
     * but in the case of ties, if two elements are of the same "class C implements Comparable<C>" type,
     * then their compareTo method is used for ordering.
     *
     * The added complexity of tree bins is worthwhile in providing worst-case O(log n) operations
     * when keys either have distinct hashes or are orderable, Thus,
     *
     * In usages with well-distributed user hashCodes, tree bins are rarely used.
     *
     * Ideally, under random hashCodes, the frequency of nodes in bins follows a Poisson distribution
     * with a parameter of about 0.5 on average for the default resizing threshold of 0.75.
     */


    private static final long serialVersionUID = 1L;

    /**
     * Ĭ�ϵĳ�ʼ��С  - ������ 2�Ĵ���
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;     // 16 init capacity

    /**
     * ���ֵ - ָ����ֵ��������С�ڸ�ֵ�� 2 �Ĵ��ݣ�
     */
    static final int MAXINUM_CAPACITY = 1 << 30;

    /**
     * �������� - ��û���ڹ��캯��ָ����ʱ��
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * ��ֵ - list -> tree
     * The bin count threshold for using a tree rather than list for a bin.
     * Bins are converted to trees when adding an element to a bin with at least this many nodes.
     * The value must be greater than 2 and should be at least 8 to mesh with assumptions in tree removal about conversion back to plain bins upon shrinkage.
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * ��ֵ - tree->list
     * The bin count threshold for untreeifying a(split) bin during a resize operation.
     * Should be less than TREEIFY_THRESHOLD, and at most 6 to mesh with shrinkage detection under removal.
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * The smallest table capacity for which bins my be treeified.
     * ? (Otherwise the table is resized if too many nodes in a bin)
     * ? Should be at least 4*TREEIFY_THRESHOLD to avoid conflicts between resizing and treeification thresholds.
     * ? ����Ϊʲô
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /**
     * Basic hash bin node, used for most entries.  (See below for
     * TreeNode subclass, and in LinkedHashMap for its Entry subclass.)
     */
    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public final V getValue() {
            return value;
        }

        @Override
        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
//                Map.Entry e = (Map.Entry)o; // how about this
                if (Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue())) {
                    return true;
                }
            }
            return false;
        }
    }

    /* ---------------- Static utilities -------------- */

    /**
     * ֮������������ hash ֵ��ԭ���ǣ���չ key.hashcode �ĸ�λ����λ��
     * Ϊ�˱�����ײ
     * ��� float ���͵� key ������С�����������ϵĳ�ͻ���������ǽ���λ��Ӱ�����ơ�
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * �Ա� x �� Class ���ͣ����ʵ����Comparable�ӿڣ����򷵻�null
     */
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c;
            Type[] ts, as;
            Type t;
            ParameterizedType p;
            if ((c = x.getClass()) == String.class) {
                return c;
            }
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; i++) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            ((p = (ParameterizedType) t).getRawType() == Comparable.class) && // �Ƿ�̳��� Comparable �ӿڣ�
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c)   // type arg is c
                        return c;
                }
            }

        }
        return null;
    }

    /**
     * @param kc
     * @param k
     * @param x
     * @return
     */
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 : ((Comparable) k).compareTo(x)); // ������ʽ����˳���д���֤
//        return (x == null || x.getClass() != kc) ? 0 : ((Comparable)k).compareTo(x); // Ӧ��������
    }

    /**
     * Returns a power of two size for the given target capacity.
     * ?
     * ���ܣ����ڵ��������������ӽ� 2�Ĵ��� ����
     * ѧϰ��Ч�ʷǳ��ߵ��㷨��������
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;    // Ϊ��ʹĿ��ֵ >= ԭֵ
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXINUM_CAPACITY) ? MAXINUM_CAPACITY : n + 1;

    }


    /* ---------------- Fields -------------- */

    /**
     * The table, initialized on first use, and resized as necessary.
     * When allocated, length is always a power of two.
     * (We also tolerate length zero in some operations to allow bootstrapping mechanics that are currently not needed.)
     * ��ĳЩ�����У�����Ҳ������Ϊ 0��������ǰ������Ҫ����������?
     * ?
     * ������Ǹ�Ͱ
     */
    transient Node<K, V>[] table;

    /**
     * Holds cached entrySet().
     * Note that AbstractMap fields are used for keySet() and values().
     * ����� Entry /?
     */
    transient Set<Map.Entry<K,V>> entrySet;

    /**
     * The number of key-value mappings contained in this map.
     */
    transient int size;

    /**
     * The number of times this HashMap has been structurally modified Structural modifications are those
     * that change the number of mappings in the HashMap or otherwise modify its internal structure(e.g., rehash).
     * This field is used to make iterators on Collection-views of the HashMap fail-fast.(See ConcurrentModificationEx)
     * ��Щ�ı�ӳ������������������ʽ�޸����ڲ��ṹ�Ĵ���
     * ���ֶ����ڼ�����ͼ�ϵĿ���ʧ�� - See ConcurrentModificationException
     */
    transient int modCount;

    /**
     * The next size value at which to resize (capacity * load factor)
     * �´ν��� resize ��ʱ�����ֵ
     * @Serial
     * The javadoc description is true upon serialization. Additionally, if the table array has not been allocated, this field holds the initial array capacity, or zero signifying DEFAULT_INITIAL_CAPACITY.
     * javadoc���������л�ʱΪtrue�����⣬�����δ��������飬����ֶα�����ʼ�������������㣬��ʾĬ�ϵ�"��ʼ"����
     * ?
     */
    int threshold;

    /**
     * The load factor for the hash table.
     * @serial ? what's this
     */
    final float loadFactor;


    /* ---------------- Public operations -------------- */

    /**
     * Constructs an empty HashMap with the specified initial capacity and load factor.
     * @param initialCapacity   the initial capacity
     * @param loadFactor        the load factor
     * @throws IllegalArgumentException     if the initial capacity is negative or the load factor is nonpositive
     */
    public HashMapT(int initialCapacity, float loadFactor){
        if(initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if(initialCapacity > MAXINUM_CAPACITY)
            initialCapacity = MAXINUM_CAPACITY;
        if(loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);

        this.loadFactor = loadFactor;
        // ? Ϊʲô��ʼֱֵ����������ֵ����Ӧ���� init*loadfactor ��
        this.threshold = tableSizeFor(initialCapacity);
    }

    // specified initial capacity and the default load factor(0.75)
    public HashMapT(int initialCapacity){
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * default initial capacity(16)  and  the default load factor(0.75)
     */
    public HashMapT(){
        this.loadFactor = DEFAULT_LOAD_FACTOR;  // all other fields defaulted
    }

    /**
     * Constructs a new HashMap with the same mappings as the specified Map.
     *
     * The HashMap is created with default load factor(0.75) and an initial capacity sufficient to hold the mappings in the specified Map.
     * HashMap��ʹ��Ĭ�ϼ������ӣ�0.75����������ָ��ӳ���б���ӳ��ĳ�ʼ���������ġ�
     *
     * @param m the map whose mappings are to be placed in this map
     */
    public HashMapT(Map<? extends K, ? extends V> m){
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m, false);
    }

    /**
     * Implements Map.putAll and Map constructor.
     *
     * @param m
     * @param evict /false/ when initially constructing this map, else true (relayed to method afterNodeInsertion).
     */
    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict){
        int s = m.size();
        if(s > 0){                                              // ǰ���Ǵ��� map �Ĵ�С��Ϊ 0
            if(table == null){  // pre-size                     // ˵���鿽������������ putMapEntries�����߹����û�Ź��κ�Ԫ��
                float ft = ((float)s / loadFactor) + 1.0f;      // ʹ�� size �պò�������ֵ��һ������������ȡ��
                int t = ((ft < (float)MAXINUM_CAPACITY) ? (int)ft : MAXINUM_CAPACITY);
                // ��Ȼ����һ�ٲ�������ֻ��������������� t > ��ǰ�ݴ���������Ż����¼���������
                if(t > threshold){
                    threshold = tableSizeFor(t);
                }
            }else if(s > threshold){    // ˵�� table �Ѿ���ʼ�����ˣ��ж���size���Ƿ���Ҫ resize()
                resize();
            }

            // ѭ����� putVal ����Ҳ�ᴥ�� resize
            for(Map.Entry<? extends K, ? extends V> e : m.entrySet()){
                // Entry ���������ֻ��ʹ��get���͵ĺ���
                K key = e.getKey();
                V value = e.getValue();
                putVal(hash(key), key, value, false, evict);
            }
        }
    }


    // Returns the number of key-value mappings in this map
    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * A return value of null does not necessarily indicate that the map contains no mapping for the key;
     * it's also possible that the map explicitly maps the key to null.
     * The containsKey operation may be used to distinguish these two cases.
     * ��� key Ϊ null �������ʹ�� containsKey() ���Էֱ�
     *
     * @param key
     * @return
     */
    public V get(Object key){
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }


    /**
     * Implements Map.get and related methods
     * ��ȡ node - ͨ�� hash & key
     * @param hash  hash for key
     * @param key   the key
     * @return  the node, or null if none
     */
    final Node<K,V> getNode(int hash, Object key){
        Node<K,V>[] tab;
        Node<K,V> first, e;
        int n;
        K k;

        if((tab = table) != null &&                     // ��ȡ hash table
                (n = tab.length) > 0 &&                 // ��Ԫ��
                (first = tab[(n-1) & hash])!= null){    // ��(n - 1) �� hash ��������ʱ���ᱣ��hash�� �� x λ�� 1�������ͱ�֤������ֵ ���ᳬ�����鳤�ȡ�
            // ��λ��ĳһ��Ͱ
            if(first.hash == hash &&                    // always check the first node
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;

            if((e=first.next) != null){                 // ���� next ʱ������������
                if(first instanceof TreeNode)           // 1 ��������νṹ
                    return ((TreeNode<K,V>first).getTreeNode(hash, key));

                do {                                    // 2 ���������ṹ
                    /**
                     * ��������:
                     * A/ hash ֵ���
                     * B/ ������ͬ  ����  equals->true
                     * ����˵���ȶ�λ��Ͱ��Ȼ��hashֵ��ȣ����Ƚ� ���� ���� equals���
                     */
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;

    }


    /**
     * Returns true if this map contains a mapping for the specified key.
     * (also can null)
     * @param key
     * @return
     */
    public boolean containsKey(Object key){
        // �жϵ��� node �Ƿ�Ϊ null�����Կ����ж� node.key = null ��������� 0 ��Ԫ��
        return getNode(hash(key), key) != null;
    }

    public boolean containsValue(Object value) {
        Node<K, V>[] tab;
        V v;
        if ((tab = table) != null && size > 0) {
            for (int i = 0; i < tab.length; i++) {
                for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                    if ((v = e.value) == value || (value != null && value.equals(v))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaces.
     *
     * @param key
     * @param value
     * @return the previous value associated with key,  ����ԭֵ
     *         or null if there was no mapping for key. ������key������null
     *         A null return can also indicate that the map previously associated null with key. ԭֵΪ null
     */
    public V put(K key, V value){
        return putVal(hash(key), key, value, false, true);
    }

    /**
     * Implements Map.put and related methods.
     *
     * @param hash hash for key
     * @param key the key
     * @param value the value to put
     * @param onlyIfAbsent  if true, don't change existing value  �����и��ǲ���
     * @param evict if false, the  table is in creation mode.
     * @return  previous value, or null if none
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict){
        Node<K,V>[] tab;        // hashmap �� Node ����
        Node<K,V> p;            // Ҫ������ node
        int n, i;               // n Ϊ��ǰhashmap��node����ĳ��ȣ�i Ϊ�����������±�
        if((tab = table) == null || (n=tab.length) == 0){   // �洢Ԫ�ص� table Ϊ��ʱ�����б�Ҫ�ֶεĳ�ʼ�� resize()
            n = (tab = resize()).length;
        }

        if((p = tab[i=(n-1) & hash]) == null){              // ����ͰΪ��ʱ��������Node
            tab[i] = newNode(hash, key, value, null);
        }else {                                              // ��Ϊ��ʱ�Ĳ��������뵽Ͱ��
            Node<K, V> e;
            K k;

            // 1 ����²���Ľڵ�� table �е� p ���� hash ֵ��key ֵ��ͬ�Ļ�
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;                                      // ��λ�����node������ e ָ����
                // 2 ��ǰ�Ǻ�������Ļ������к��������
            } else if (p instanceof TreeNode) {
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
                // 3 ��ǰ���Ǻ����
            } else {
                for (int binCount = 0; ; ++binCount) {                // ����Ա�
                    // Q:null
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1)       // -1 for first �����ȴ���8ʱ��������ת��Ϊ�������8 - 1 + 1(����) + headP = 9��Ԫ��?
                            treeifyBin(tab, hash);                  // ����
                        break;
                    }
                    // not null
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        break;

                    p = e;  // ������һ�ε���
                }
            }

            // A ����������ӳ��͸���
            if (e != null) {  // existing mapping for key
                V oldValue = e.value;
                // �ж��Ƿ������ǣ����� value �Ƿ�Ϊ��
                if (!onlyIfAbsent || oldValue == null) {
                    e.value = value;
                }
                afterNodeAccess(e); // �ص������� LinkedHashMap ���ò��� ?
                return oldValue;
            }// ���������û�и��Ľṹ - []Nodes
        }

        // B ���������, �ο� Q ��
        ++modCount;                // ���Ĳ�������
        if (++size > threshold) {     // �����ٽ�ֵ����ֵ ? size > 16 * .75 Ҳ����˵��Ĭ�� >12 Ԫ�غ󣬽�����������
            // ����������Ϊԭ���� 2 ��,����ԭ��������Ԫ�طŵ���������
            // ��Ϊ������������ȣ���˻�Ҫ��������
            resize();
        }
        afterNodeInsertion(evict);  // �ص������� LinkedHashMap ���ò��� ?
        return null;

    }


    /**
     * Initializes or doubles table size.
     * ��ʼ�� �� �ӱ����С��
     * If null, allocates in accord with initial capacity target held in field threshold.
     *
     * Otherwise, because we are using power-of-two expansion, the elements from each bin must
     * either stay at same index, or move with a power of two offset in the new table.
     * ������Ϊ����ʹ�õ��Ƕ����ݣ�ÿ��Ͱ�е�Ԫ�أ�Ҫô��������ͬ���±꣬Ҫô���±����� 2 �Ĵ���ƫ�����ƶ���
     *
     */
    final Node<K,V>[] resize(){
        Node<K,V>[] oldTab = table;
        // ���ڵ� hash ����������һ�γ�ʼ��ʱΪ 0
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        // ���ڵ������ٽ�ֵ
        int oldThr = threshold;

        int newCap, newThr = 0;
        // hash ��������Ϊ 0 ʱ
        if(oldCap > 0){
            /**
             * ��� hash �������Ѵﵽ����ٽ�ֵ���򷵻�ԭ���飬���������ٽ�ֵ���ֲ��䣬
             * ������������һ�������ݺ�ı��ܴ�������ֵ��
             */
            if (oldCap >= MAXINUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXINUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY) {
                newThr = oldThr << 1;   // double threshold ��ֵ����
            }
        }else if(oldThr > 0){
            newCap = oldThr;        // ��������ֵ��
        }else{
            newCap = DEFAULT_INITIAL_CAPACITY;      // ��һ�δ�����ʹ��Ĭ��ֵ������ز���
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }

        // ��һ�����ݳ�ʼ����ֵ?
        if(newThr == 0){
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXINUM_CAPACITY && ft < (float)MAXINUM_CAPACITY ? (int) ft : Integer.MAX_VALUE);
        }
        // ���������ٽ�ֵ
        threshold = newThr;

        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        // �Ѵ��� hash ��
        if(oldTab != null){
            // ���� hahs ���е�ÿ��Ͱ
            for (int j = 0; j < oldCap; ++j) {
                // ��ʱ node ������ָ���Ͱ�еĽ��Ԫ��
                Node<K,V> e;
                // ����ɵ� hash ��ĵ�ǰͰλ�ô��ڽ�㣬����ֵ�� e
                if((e=oldTab[j]) != null){
                    // ���Ͱ��Ϊ null
                    oldTab[j] = null;
                    if (e.next == null) {                                    // ���ȡ�����Ľ�㲻������һ��Ԫ�أ������¼����Ӧ���� hash Ͱ��λ��
                        newTab[e.hash & (newCap - 1)] = e;
                    } else if (e instanceof TreeNode) {                       // ����� ?
                        ((TreeNode < K, V > e).split(this, newTab, j, oldCap));
                    } else {                                                 // ����
                        Node<K, V> loHead = null, loTail = null;            // ԭͰλ��
                        Node<K, V> hiHead = null, hiTail = null;            // ��Ͱλ�ã�������һ�����λ��
                        Node<K, V> next;    // ��һ�����
                        do {
                            next = e.next;  // ָ����һ�����
                            if ((e.hash & oldCap) == 0) {                   // �жϵ�ǰ���� hash ֵ�� (����ʱ���룺tab[i=(n-1) & hash])
                                                                            // ��hash�������� 1 λ�Ķ�����λ�Ƿ�Ϊ 1��
                                                                            // ���Ϊ0�����λ�ñ���ԭͰ�����Ϊ1������Ͱ
                                if (loTail == null) {           // ԭͰλ�õ�����β
                                    loHead = e;                 // ����ǰ�������Ϊ����ͷ
                                } else {
                                    loTail.next = e;
                                }
                                loTail = e;                     // ���õ�ǰβ���
                            } else {
                                if (hiTail == null) {
                                    hiHead = e;
                                } else {
                                    hiTail.next = e;
                                }
                                hiTail = e;
                            }
                        } while ((e = next) != null);

                        if(loTail != null){                 // ԭλ�ô�������
                            loTail.next = null;
                            newTab[j] = loHead;             // ԭ hash λ��
                        }

                        if(hiTail != null){                 // ��Ͱλ�ô�������
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;    // ��ǰ Ͱλ�� + �ɵ�hash ������
                        }

                    }
                }
            }
        }
        return newTab;
    }


    /**
     * Replaces all linked nodes in bin at index for given hash unless table is too small,
     * in which case resizes instead.
     *
     * ���������Ԫ�ر�����ṹ��
     *
     * �������ȴ��ڵ��� 9 ������ø÷�������
     *
     * @param tab
     * @param hash
     */
    final void treeifyBin(Node<K,V>[] tab, int hash){
        int n, index;
        Node<K,V> e;
        /**
         * ���Ͱ����Ϊ��  ����  Ͱ���鳤�� С��  ���ṹ������С���� -
         * MIN_TREEFIY_CAPACITY Ĭ��ֵ�� 64���������ֵ�������Ϊ�����Ͱ���鳤��С�����ֵ��û�б�Ҫȥ���нṹת��
         * ��һ������λ���ϼ����˶����ֵ�ԣ�������Ϊ��Щ key �� hash ֵ�����鳤�� ������ ������ͬ������������Щ key �� hash ֵ��ͬ��
         * ��Ϊ hash ֵ��ͬ�ĸ��ʲ��ߣ����Կ���ͨ�����ݵķ�ʽ����ʹ��������Щ key �� hash ֵ�ں��µ����鳤��ȡģ֮�󣬲�ֵ��������λ���ϡ�
         */
        if(tab == null || (n=tab.length) < MIN_TREEIFY_CAPACITY){
            resize();

        // ���������ֵ����ô���б�Ҫ���нṹת���ˣ����� hash ֵ�����鳤�Ƚ���ȡģ����󣬵õ�������׽��
        } else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<K,V> hd = null, tl = null;     // ������/β�ڵ�
            do{
                // ���ý��ת��Ϊ�����
                TreeNode<K,V> p = replacementTreeNode(e, null);
                if(tl == null){     // ���β���Ϊ�գ�˵����û�и����
                    hd = p;         // �׽�㣨����㣩ָ��ǰ���
                }else{              // β��㲻Ϊ�գ�����������һ��˫������ṹ
                    p.prev = tl;    // ��ǰ������ǰһ�����ָ��β���
                    tl.next = p;    // β���� ��һ�����ָ��ǰ���
                }
                tl = p;             // ��ǰ�������Ϊβ���
            }while((e=e.next) != null); // ������������

            /**
             * ��ĿǰΪֹ��Ҳֻ�ǰ� Node ����ת������ TreeNode ���󣬰ѵ�������ת������˫������
             */

            // ��ת��֮���˫�������滻ԭ��λ���ϵĵ�������
            if((tab[index] = hd) !=null){
                hd.treeify(tab);        // �˴���������
            }
        }
    }

    public void putAll(Map<? extends K, ? extends V> m){
        putMapEntries(m, true);
    }

    public V remove(Object key){
        Node<K,V> e;
        return (e = removeNode(hash(key), key, null, false, true)) == null ? null : e.value;
    }

    /**
     * Implements Map.remove and related methods.
     *
     * @param hash
     * @param key
     * @param value         the value to match if matchValue, else ignored
     * @param matchValue    if true only remove if value is equal
     * @param movable       if false do not move other nodes while removing ? tree ?
     * @return the node, or null if none
     */
    final Node<K,V> removeNode(int hash, Object key, Object value, boolean matchValue, boolean movable){
        Node<K,V>[] tab;
        Node<K,V> p;
        int n, index;
        // �� table ��Ϊ�գ����� hash ��Ӧ��Ͱ��Ϊ��ʱ
        if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) {
            Node<K, V> node = null, e;
            K k;
            V v;
            // ��Ͱ�е� ͷ��� ����Ҫɾ���Ľ��
            if(p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                // �� node ��¼Ҫɾ����ͷ���
                node = p;
            // ͷ��㲻��Ҫɾ���Ľ�㣬����ͷ�����н��
            }else if((e = p.next) != null){
                // ͷ��� Ϊ ����㣬�����������Ҫɾ���Ľ��
                if(p instanceof TreeNode){
                    node = ((TreeNode<K,V>)p).getTreeNode(hash, key);
                // ͷ��� Ϊ ������
                }else{
                    // ��������
                    do{
                        // hash ֵ��� && ��key��ַ���  ����  equals��
                        if(e.hash == hash && ((k=e.key) == key || (key != null && key.equals(k)))){
                            // node ��¼Ҫɾ���Ľ��
                            node = e;
                            break;
                        }
                        // p ���浱ǰ�������Ľ��
                        p = e;
                    }while((e = e.next) != null);
                }
            }
            // Ҫ�ҵĽ�㲻Ϊ�գ����ҡ����� matchValue ?
            if (node != null && (!matchValue || (v = node.value) == value || (value != null && value.equals(v)))) {
                // ������ɾ�����
                if(node instanceof TreeNode){
                    ((TreeNode<K,V>) node).removeTreeNode(this, tab, movable);
                // ɾ������ ͷ���
                }else if(node == p){
                    tab[index] = node.next;
                // ����ͷ��㣬ָ����һ��
                }else{
                    p.next = node.next;
                }
                ++ modCount;
                -- size;
                afterNodeRemoval(node);
                return node;
            }
        }
        return null;
    }

    public void clear(){
        Node<K,V>[] tab;
        modCount++;
        if((tab = table) != null && size > 0){
            size = 0;
            for (int i = 0; i < tab.length; ++i) {
                tab[i] = null;
            }
        }
    }


    /**
     * Returns a Set view.
     * The set is backed by the map, so changes to the map are reflected in the set, and vice-versa.
     * ������ map ֧�֣����� map �ĸ��Ľ���ӳ��set�У���֮��Ȼ��
     * Iterator.remove/Set.remove/removeAll/retainAll/clear  OK @!   other, modCount, fail-fast!
     *
     * It does not support the add or addAll operations.
     *
     * @return
     */
    public Set<K> keySet(){
        Set<K> ks = keySet;
        if(ks == null){
            ks = new KeySet();
            keySet = ks;
        }
        return ks;
    }

    final class KeySet extends AbstractSet<K> {
        @Override
        public int size() {
            return size;
        }
        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
        public final boolean contains(Object o){
            return containsKey(o);
        }
        public final void clear(){
            HashMapT.this.clear();
        }
        public final boolean remove(Object key){
            return removeNode(hash(key), key, null, false, true) != null;
        }
        //?
        public final Spliterator<K> spliterator(){
            return new KeySpliterator<>(HashMap.this, 0, -1, 0, 0);
        }

        // ��ÿ�� node ���� accept ����
        public final void forEach(Consumer<? super K> action) {
            Node<K, V>[] tab;
            if (action == null)
                throw new NullPointerException();
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; i++) {
                    for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                        action.accept(e.key);
                    }
                }
                if (modCount != mc) {
                    throw new ConcurrentModificationException();
                }
            }
        }

    }

    /**
     * �ο� keySet
     * @return
     */
    public Collection<V> values(){
        Collection<V> vs = values;
        if(vs == null){
            vs = new Values();
            values = vs;
        }
        return vs;
    }

    final class Values extends AbstractCollection<V>{
        public final int size(){
            return size;
        }
        public final void clear(){
            HashMapT.this.clear();
        }
        public final Iterator<V> iterator(){
            return new ValueIterator();
        }
        public final boolean contains(Object o){
            return containsValue(o);
        }
        public final Spliterator<V> spliterator(){
            return new ValueSpliterator<>(HashMapT.this, 0, -1, 0, 0);
        }
        public final void forEach(Consumer<? super V> action){
            Node<K,V>[] tab;
            if(action == null){
                throw new NullPointerException();
            }
            if(size > 0 && (tab = table) != null){
                int mc = modCount;
                for(int i=0; i<tab.length; ++i){
                    for(Node<K,V> e = tab[i]; e!=null; e = e.next){
                        action.accept(e.value);
                    }
                }
                if(modCount != mc){             // ��������
                    throw new ConcurrentModificationException();
                }
            }
        }
    }

    public Set<Map.Entry<K, V>> entrySet(){
        Set<Map.Entry<K,V>> es;
        return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
    }

    final class EntrySet extends AbstractSet<Map.Entry<K,V>>{
        public final int size()                 { return size; }
        public final void clear()               { HashMap.this.clear(); }
        public final Iterator<Map.Entry<K,V>> iterator() {
            return new EntryIterator();
        }
        public final boolean contains(Object o){
            if(!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>) o;
            Object key = e.getKey();
            Node<K,V> candidate = getNode(hash(key), key);
            return candidate != null && candidate.equals(e);
        }
        public final boolean remove(Object o){
            if(o instanceof Map.Entry){
                Map.Entry<?,?> e = (Map.Entry<?,?>) o;
                Object key = e.getKey();
                Object value = e.getValue();
                return removeNode(hash(key), key, value, true, true) != null;
            }
            return false;
        }
        // ?
        public final Spliterator<Map.Entry<K,V>> spliterator() {
            return new EntrySpliterator<>(HashMap.this, 0, -1, 0, 0);
        }

        public final void forEach(Consumer<? super Map.Entry<K,V>> action){
            Node<K,V>[] tab;
            if(action == null){
                throw new NullPointerException();
            }
            if(size > 0 && (tab = table) != null){
                int mc = modCount;
                for (int i = 0; i < tab.length; i++) {
                    for(Node<K,V> e = tab[i]; e != null; e = e.next){
                        action.accept(e);
                    }
                }
                if(modCount != mc){
                    throw new ConcurrentModificationException();
                }
            }
        }

    }

    /**
     * ��д JDK 8 ��չ����
     */
    @Override
    public V getOrDefault(Object key, V defaultValue){
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? defaultValue : e.value;
    }

    @Override
    public V putIfAbsent(K key, V value){
        return putVal(hash(key), key, value, true, true);
    }

    @Override
    public boolean remove(Object key, Object value){
        return removeNode(hash(key), key, value, true, true) != null;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue){
        Node<K,V> e;
        V v;
        if((e = getNode(hash(key), key)) != null &&
                ((v = e.value) == oldValue || v.equals(oldValue))){
            e.value = newValue;
            afterNodeAccess(e);
            return true;
        }
        return false;
    }

    public V replace(K key, V value){
        Node<K,V> e;
        if((e = getNode(hash(key), key)) != null){
            V oldValue = e.value;
            e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
        return null;
    }


    @Override
    public V merge(K key, V value,
                   BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if (value == null)
            throw new NullPointerException();
        if (remappingFunction == null)
            throw new NullPointerException();
        int hash = hash(key);
        Node<K,V>[] tab; Node<K,V> first; int n, i;
        int binCount = 0;
        TreeNode<K,V> t = null;
        Node<K,V> old = null;
        if (size > threshold || (tab = table) == null ||
                (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((first = tab[i = (n - 1) & hash]) != null) {
            if (first instanceof TreeNode)
                old = (t = (TreeNode<K,V>)first).getTreeNode(hash, key);
            else {
                Node<K,V> e = first; K k;
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        old = e;
                        break;
                    }
                    ++binCount;
                } while ((e = e.next) != null);
            }
        }
        if (old != null) {
            V v;
            if (old.value != null)
                v = remappingFunction.apply(old.value, value);
            else
                v = value;
            if (v != null) {
                old.value = v;
                afterNodeAccess(old);
            }
            else
                removeNode(hash, key, null, false, true);
            return v;
        }
        if (value != null) {
            if (t != null)
                t.putTreeVal(this, tab, hash, key, value);
            else {
                tab[i] = newNode(hash, key, value, first);
                if (binCount >= TREEIFY_THRESHOLD - 1)
                    treeifyBin(tab, hash);
            }
            ++modCount;
            ++size;
            afterNodeInsertion(true);
        }
        return value;
    }


    /**
     * ���ô˷���ʱ��ʵ�� BiConsumer �ӿ���д accept(Object o, Object o2) �������ɸ����Լ���ʵ�ֶ� map �������ݽ��д���
     * @param action
     */
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Node<K,V>[] tab;
        if (action == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            int mc = modCount;
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K,V> e = tab[i]; e != null; e = e.next)
                    action.accept(e.key, e.value);
            }
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    /*���ô˷���ʱ��дBiFunction��Object apply(Object o, Object o2)����������oΪkey��o2Ϊvalue��������д�����߼��������¸�ֵ*/
    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Node<K,V>[] tab;
        if (function == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            int mc = modCount;
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K,V> e = tab[i]; e != null; e = e.next) {
                    e.value = function.apply(e.key, e.value);
                }
            }
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    /**
     * �������ʵ����ǳ����������ֵ����û�б���¡����Ȼ��ԭ����
     * @return
     */
    public Object clone(){
        HashMapT<K,V> result;
        try{
            result = (HashMapT<K,V>) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        result.reinitialize();
        result.putMapEntries(this, false);
        return result;
    }

    // These methods are also used when serializing HashSets
    final float loadFactor(){
        return loadFactor;
    }
    final int capacity(){
        return (table != null) ? table.length : (threshold > 0) ? threshold : DEFAULT_INITIAL_CAPACITY;
    }


    /**
     * Save the state of the <tt>HashMap</tt> instance to a stream (i.e.,
     * serialize it).
     *
     * @serialData The <i>capacity</i> of the HashMap (the length of the
     *             bucket array) is emitted (int), followed by the
     *             <i>size</i> (an int, the number of key-value
     *             mappings), followed by the key (Object) and value (Object)
     *             for each key-value mapping.  The key-value mappings are
     *             emitted in no particular order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws IOException {
        int buckets = capacity();
        // Write out the threshold, loadfactor, and any hidden stuff
        s.defaultWriteObject();
        s.writeInt(buckets);
        s.writeInt(size);
        internalWriteEntries(s);
    }

    /**
     * Reconstitutes this map from a stream (that is, deserializes it).
     * @param s the stream
     * @throws ClassNotFoundException if the class of a serialized object
     *         could not be found
     * @throws IOException if an I/O error occurs
     */
    private void readObject(java.io.ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        // Read in the threshold (ignored), loadfactor, and any hidden stuff
        s.defaultReadObject();
        reinitialize();
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new InvalidObjectException("Illegal load factor: " +
                    loadFactor);
        s.readInt();                // Read and ignore number of buckets
        int mappings = s.readInt(); // Read number of mappings (size)
        if (mappings < 0)
            throw new InvalidObjectException("Illegal mappings count: " +
                    mappings);
        else if (mappings > 0) { // (if zero, use defaults)
            // Size the table using given load factor only if within
            // range of 0.25...4.0
            float lf = Math.min(Math.max(0.25f, loadFactor), 4.0f);
            float fc = (float)mappings / lf + 1.0f;
            int cap = ((fc < DEFAULT_INITIAL_CAPACITY) ?
                    DEFAULT_INITIAL_CAPACITY :
                    (fc >= MAXIMUM_CAPACITY) ?
                            MAXIMUM_CAPACITY :
                            tableSizeFor((int)fc));
            float ft = (float)cap * lf;
            threshold = ((cap < MAXIMUM_CAPACITY && ft < MAXIMUM_CAPACITY) ?
                    (int)ft : Integer.MAX_VALUE);

            // Check Map.Entry[].class since it's the nearest public type to
            // what we're actually creating.
            SharedSecrets.getJavaOISAccess().checkArray(s, Map.Entry[].class, cap);
            @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] tab = (Node<K,V>[])new Node[cap];
            table = tab;

            // Read the keys and values, and put the mappings in the HashMap
            for (int i = 0; i < mappings; i++) {
                @SuppressWarnings("unchecked")
                K key = (K) s.readObject();
                @SuppressWarnings("unchecked")
                V value = (V) s.readObject();
                putVal(hash(key), key, value, false, false);
            }
        }
    }

    abstract class HashIterator{
        Node<K,V> next;         // next entry to return
        Node<K,V> current;      // current entry
        int expectedModCount;   // for fast-fail
        int index;              // current slot

        HashIterator(){
            expectedModCount = modCount;
            Node<K,V>[] t = table;
            current = next = null;
            index = 0;
            if(t != null  && size > 0){ // advance to first entry
                do{}while(index < t.length && (nect = t[index++]) == null);
            }
        }

        public final boolean hasNext(){
            return next != null;
        }

        final Node<K,V> nextNode() {
            Node<K,V>[] t;
            Node<K,V> e = next;
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (e == null)
                throw new NoSuchElementException();
            // ��ǰͰ�����ҹ��ˣ�ȥ��һ��Ͱ����
            if ((next = (current = e).next) == null && (t = table) != null) {
                do {} while (index < t.length && (next = t[index++]) == null);
            }
            return e;
        }

        /**
         * ���� 2 �� remove �ᱨ��
         */
        public final void remove(){
            Node<K,V> p = current;
            if(p == null){
                throw new IllegalStateException();
            }
            if(modCount != expectedModCount){
                throw new ConcurrentModificationException();
            }
            current = null;
            K key = p.key;
            removeNode(hash(key), key, null, false, false);
            expectedModCount = modCount;
        }
    }

    final class KeyIterator extends HashIterator
            implements Iterator<K> {
        public final K next() { return nextNode().key; }
    }

    final class ValueIterator extends HashIterator
            implements Iterator<V> {
        public final V next() { return nextNode().value; }
    }

    final class EntryIterator extends HashIterator
            implements Iterator<Map.Entry<K,V>> {
        public final Map.Entry<K,V> next() { return nextNode(); }
    }


    // spliterators

    /* ------------------------------------------------------------ */
    // spliterators

    static class HashMapSpliterator<K,V> {
        final HashMap<K,V> map;
        Node<K,V> current;          // current node
        int index;                  // current index, modified on advance/split
        int fence;                  // one past last index
        int est;                    // size estimate
        int expectedModCount;       // for comodification checks

        HashMapSpliterator(HashMap<K,V> m, int origin,
                           int fence, int est,
                           int expectedModCount) {
            this.map = m;
            this.index = origin;
            this.fence = fence;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }

        final int getFence() { // initialize fence and size on first use
            int hi;
            if ((hi = fence) < 0) {
                HashMap<K,V> m = map;
                est = m.size;
                expectedModCount = m.modCount;
                Node<K,V>[] tab = m.table;
                hi = fence = (tab == null) ? 0 : tab.length;
            }
            return hi;
        }

        public final long estimateSize() {
            getFence(); // force init
            return (long) est;
        }
    }

    static final class KeySpliterator<K,V>
            extends HashMapSpliterator<K,V>
            implements Spliterator<K> {
        KeySpliterator(HashMap<K,V> m, int origin, int fence, int est,
                       int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }

        public KeySpliterator<K,V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null :
                    new KeySpliterator<>(map, lo, index = mid, est >>>= 1,
                            expectedModCount);
        }

        public void forEachRemaining(Consumer<? super K> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K,V> m = map;
            Node<K,V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            }
            else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                    (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K,V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p.key);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }

        public boolean tryAdvance(Consumer<? super K> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K,V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        K k = current.key;
                        current = current.next;
                        action.accept(k);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }

        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0) |
                    Spliterator.DISTINCT;
        }
    }

    static final class ValueSpliterator<K,V>
            extends HashMapSpliterator<K,V>
            implements Spliterator<V> {
        ValueSpliterator(HashMap<K,V> m, int origin, int fence, int est,
                         int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }

        public ValueSpliterator<K,V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null :
                    new ValueSpliterator<>(map, lo, index = mid, est >>>= 1,
                            expectedModCount);
        }

        public void forEachRemaining(Consumer<? super V> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K,V> m = map;
            Node<K,V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            }
            else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                    (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K,V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p.value);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }

        public boolean tryAdvance(Consumer<? super V> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K,V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        V v = current.value;
                        current = current.next;
                        action.accept(v);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }

        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0);
        }
    }

    static final class EntrySpliterator<K,V>
            extends HashMapSpliterator<K,V>
            implements Spliterator<Map.Entry<K,V>> {
        EntrySpliterator(HashMap<K,V> m, int origin, int fence, int est,
                         int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }

        public EntrySpliterator<K,V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null :
                    new EntrySpliterator<>(map, lo, index = mid, est >>>= 1,
                            expectedModCount);
        }

        public void forEachRemaining(Consumer<? super Map.Entry<K,V>> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K,V> m = map;
            Node<K,V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            }
            else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                    (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K,V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }

        public boolean tryAdvance(Consumer<? super Map.Entry<K,V>> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K,V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        Node<K,V> e = current;
                        current = current.next;
                        action.accept(e);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }

        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0) |
                    Spliterator.DISTINCT;
        }
    }



    /* ------------------------------------------------------------ */
    // LinkedHashMap support

    /**
     * The following package-protected methods are designed to be overridden by LinkedHashMap, but not by any other subclass.
     *
     * Nearly all other internal methods are also package-protected but are declared final,
     * so can be used by LinkedHashMap, view classes, and HashSet.
     */

    // Create a regular (non-tree) node
    Node<K,V> newNode(int hash, K key, V value, Node<K, V> next){
        return new Node<>(hash, key, value, next);
    }

    // For conversion from TreeNodes to plain nodes
    Node<K,V> replacementNode(Node<K,V> p, Node<K,V> next){
        return new Node<>(p.hash, p.key, p.value, next);
    }


    // Create a tree bin node
    TreeNode<K,V> newTreeNode(int hash, K key, V value, Node<K,V> next) {
        return new TreeNode<>(hash, key, value, next);
    }

    // For treeifyBin
    TreeNode<K,V> replacementTreeNode(Node<K,V> p, Node<K,V> next) {
        return new TreeNode<>(p.hash, p.key, p.value, next);
    }


    /**
     * Reset to initial default state.  Called by clone and readObject.
     */
    void reinitialize() {
        table = null;
        entrySet = null;
        keySet = null;
        values = null;
        modCount = 0;
        threshold = 0;
        size = 0;
    }
    // Callbacks to allow LinkedHashMap post-actions
    void afterNodeAccess(Node<K,V> p) { }
    void afterNodeInsertion(boolean evict) { }
    void afterNodeRemoval(Node<K,V> p) { }

    // Called only from writeObject, to ensure compatible ordering.
    void internalWriteEntries(java.io.ObjectOutputStream s) throws IOException {
        Node<K,V>[] tab;
        if (size > 0 && (tab = table) != null) {
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K,V> e = tab[i]; e != null; e = e.next) {
                    s.writeObject(e.key);
                    s.writeObject(e.value);
                }
            }
        }
    }



    /**
     * Entry for Tree bins. Extends LinkedHashMap.Entry (which in turn
     * extends Node) so can be used as extension of either regular or
     * linked node.
     */
    static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
        TreeNode<K,V> parent;  // red-black tree links
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        boolean red;
        TreeNode(int hash, K key, V val, Node<K,V> next) {
            super(hash, key, val, next);
        }

        /**
         * Returns root of tree containing this node.
         */
        final TreeNode<K,V> root() {
            for (TreeNode<K,V> r = this, p;;) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }

        /**
         * Ensures that the given root is the first node of its bin.
         */
        static <K,V> void moveRootToFront(Node<K,V>[] tab, TreeNode<K,V> root) {
            int n;
            if (root != null && tab != null && (n = tab.length) > 0) {
                int index = (n - 1) & root.hash;
                TreeNode<K,V> first = (TreeNode<K,V>)tab[index];
                if (root != first) {
                    Node<K,V> rn;
                    tab[index] = root;
                    TreeNode<K,V> rp = root.prev;
                    if ((rn = root.next) != null)
                        ((TreeNode<K,V>)rn).prev = rp;
                    if (rp != null)
                        rp.next = rn;
                    if (first != null)
                        first.prev = root;
                    root.next = first;
                    root.prev = null;
                }
                assert checkInvariants(root);
            }
        }

        /**
         * Finds the node starting at root p with the given hash and key.
         * The kc argument caches comparableClassFor(key) upon first use
         * comparing keys.
         */
        final TreeNode<K,V> find(int h, Object k, Class<?> kc) {
            TreeNode<K,V> p = this;
            do {
                int ph, dir; K pk;
                TreeNode<K,V> pl = p.left, pr = p.right, q;
                if ((ph = p.hash) > h)
                    p = pl;
                else if (ph < h)
                    p = pr;
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if (pl == null)
                    p = pr;
                else if (pr == null)
                    p = pl;
                else if ((kc != null ||
                        (kc = comparableClassFor(k)) != null) &&
                        (dir = compareComparables(kc, k, pk)) != 0)
                    p = (dir < 0) ? pl : pr;
                else if ((q = pr.find(h, k, kc)) != null)
                    return q;
                else
                    p = pl;
            } while (p != null);
            return null;
        }

        /**
         * Calls find for root node.
         */
        final TreeNode<K,V> getTreeNode(int h, Object k) {
            return ((parent != null) ? root() : this).find(h, k, null);
        }

        /**
         * Tie-breaking utility for ordering insertions when equal
         * hashCodes and non-comparable. We don't require a total
         * order, just a consistent insertion rule to maintain
         * equivalence across rebalancings. Tie-breaking further than
         * necessary simplifies testing a bit.
         */
        static int tieBreakOrder(Object a, Object b) {
            int d;
            if (a == null || b == null ||
                    (d = a.getClass().getName().
                            compareTo(b.getClass().getName())) == 0)
                d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
                        -1 : 1);
            return d;
        }

        /**
         * Forms tree of the nodes linked from this node.
         */
        final void treeify(Node<K,V>[] tab) {
            TreeNode<K,V> root = null;
            for (TreeNode<K,V> x = this, next; x != null; x = next) {
                next = (TreeNode<K,V>)x.next;
                x.left = x.right = null;
                if (root == null) {
                    x.parent = null;
                    x.red = false;
                    root = x;
                }
                else {
                    K k = x.key;
                    int h = x.hash;
                    Class<?> kc = null;
                    for (TreeNode<K,V> p = root;;) {
                        int dir, ph;
                        K pk = p.key;
                        if ((ph = p.hash) > h)
                            dir = -1;
                        else if (ph < h)
                            dir = 1;
                        else if ((kc == null &&
                                (kc = comparableClassFor(k)) == null) ||
                                (dir = compareComparables(kc, k, pk)) == 0)
                            dir = tieBreakOrder(k, pk);

                        TreeNode<K,V> xp = p;
                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
                            x.parent = xp;
                            if (dir <= 0)
                                xp.left = x;
                            else
                                xp.right = x;
                            root = balanceInsertion(root, x);
                            break;
                        }
                    }
                }
            }
            moveRootToFront(tab, root);
        }

        /**
         * Returns a list of non-TreeNodes replacing those linked from
         * this node.
         */
        final Node<K,V> untreeify(HashMap<K,V> map) {
            Node<K,V> hd = null, tl = null;
            for (Node<K,V> q = this; q != null; q = q.next) {
                Node<K,V> p = map.replacementNode(q, null);
                if (tl == null)
                    hd = p;
                else
                    tl.next = p;
                tl = p;
            }
            return hd;
        }

        /**
         * Tree version of putVal.
         */
        final TreeNode<K,V> putTreeVal(HashMap<K,V> map, Node<K,V>[] tab,
                                       int h, K k, V v) {
            Class<?> kc = null;
            boolean searched = false;
            TreeNode<K,V> root = (parent != null) ? root() : this;
            for (TreeNode<K,V> p = root;;) {
                int dir, ph; K pk;
                if ((ph = p.hash) > h)
                    dir = -1;
                else if (ph < h)
                    dir = 1;
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if ((kc == null &&
                        (kc = comparableClassFor(k)) == null) ||
                        (dir = compareComparables(kc, k, pk)) == 0) {
                    if (!searched) {
                        TreeNode<K,V> q, ch;
                        searched = true;
                        if (((ch = p.left) != null &&
                                (q = ch.find(h, k, kc)) != null) ||
                                ((ch = p.right) != null &&
                                        (q = ch.find(h, k, kc)) != null))
                            return q;
                    }
                    dir = tieBreakOrder(k, pk);
                }

                TreeNode<K,V> xp = p;
                if ((p = (dir <= 0) ? p.left : p.right) == null) {
                    Node<K,V> xpn = xp.next;
                    TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn);
                    if (dir <= 0)
                        xp.left = x;
                    else
                        xp.right = x;
                    xp.next = x;
                    x.parent = x.prev = xp;
                    if (xpn != null)
                        ((TreeNode<K,V>)xpn).prev = x;
                    moveRootToFront(tab, balanceInsertion(root, x));
                    return null;
                }
            }
        }

        /**
         * Removes the given node, that must be present before this call.
         * This is messier than typical red-black deletion code because we
         * cannot swap the contents of an interior node with a leaf
         * successor that is pinned by "next" pointers that are accessible
         * independently during traversal. So instead we swap the tree
         * linkages. If the current tree appears to have too few nodes,
         * the bin is converted back to a plain bin. (The test triggers
         * somewhere between 2 and 6 nodes, depending on tree structure).
         */
        final void removeTreeNode(HashMap<K,V> map, Node<K,V>[] tab,
                                  boolean movable) {
            int n;
            if (tab == null || (n = tab.length) == 0)
                return;
            int index = (n - 1) & hash;
            TreeNode<K,V> first = (TreeNode<K,V>)tab[index], root = first, rl;
            TreeNode<K,V> succ = (TreeNode<K,V>)next, pred = prev;
            if (pred == null)
                tab[index] = first = succ;
            else
                pred.next = succ;
            if (succ != null)
                succ.prev = pred;
            if (first == null)
                return;
            if (root.parent != null)
                root = root.root();
            if (root == null
                    || (movable
                    && (root.right == null
                    || (rl = root.left) == null
                    || rl.left == null))) {
                tab[index] = first.untreeify(map);  // too small
                return;
            }
            TreeNode<K,V> p = this, pl = left, pr = right, replacement;
            if (pl != null && pr != null) {
                TreeNode<K,V> s = pr, sl;
                while ((sl = s.left) != null) // find successor
                    s = sl;
                boolean c = s.red; s.red = p.red; p.red = c; // swap colors
                TreeNode<K,V> sr = s.right;
                TreeNode<K,V> pp = p.parent;
                if (s == pr) { // p was s's direct parent
                    p.parent = s;
                    s.right = p;
                }
                else {
                    TreeNode<K,V> sp = s.parent;
                    if ((p.parent = sp) != null) {
                        if (s == sp.left)
                            sp.left = p;
                        else
                            sp.right = p;
                    }
                    if ((s.right = pr) != null)
                        pr.parent = s;
                }
                p.left = null;
                if ((p.right = sr) != null)
                    sr.parent = p;
                if ((s.left = pl) != null)
                    pl.parent = s;
                if ((s.parent = pp) == null)
                    root = s;
                else if (p == pp.left)
                    pp.left = s;
                else
                    pp.right = s;
                if (sr != null)
                    replacement = sr;
                else
                    replacement = p;
            }
            else if (pl != null)
                replacement = pl;
            else if (pr != null)
                replacement = pr;
            else
                replacement = p;
            if (replacement != p) {
                TreeNode<K,V> pp = replacement.parent = p.parent;
                if (pp == null)
                    root = replacement;
                else if (p == pp.left)
                    pp.left = replacement;
                else
                    pp.right = replacement;
                p.left = p.right = p.parent = null;
            }

            TreeNode<K,V> r = p.red ? root : balanceDeletion(root, replacement);

            if (replacement == p) {  // detach
                TreeNode<K,V> pp = p.parent;
                p.parent = null;
                if (pp != null) {
                    if (p == pp.left)
                        pp.left = null;
                    else if (p == pp.right)
                        pp.right = null;
                }
            }
            if (movable)
                moveRootToFront(tab, r);
        }

        /**
         * Splits nodes in a tree bin into lower and upper tree bins,
         * or untreeifies if now too small. Called only from resize;
         * see above discussion about split bits and indices.
         *
         * @param map the map
         * @param tab the table for recording bin heads
         * @param index the index of the table being split
         * @param bit the bit of hash to split on
         */
        final void split(HashMap<K,V> map, Node<K,V>[] tab, int index, int bit) {
            TreeNode<K,V> b = this;
            // Relink into lo and hi lists, preserving order
            TreeNode<K,V> loHead = null, loTail = null;
            TreeNode<K,V> hiHead = null, hiTail = null;
            int lc = 0, hc = 0;
            for (TreeNode<K,V> e = b, next; e != null; e = next) {
                next = (TreeNode<K,V>)e.next;
                e.next = null;
                if ((e.hash & bit) == 0) {
                    if ((e.prev = loTail) == null)
                        loHead = e;
                    else
                        loTail.next = e;
                    loTail = e;
                    ++lc;
                }
                else {
                    if ((e.prev = hiTail) == null)
                        hiHead = e;
                    else
                        hiTail.next = e;
                    hiTail = e;
                    ++hc;
                }
            }

            if (loHead != null) {
                if (lc <= UNTREEIFY_THRESHOLD)
                    tab[index] = loHead.untreeify(map);
                else {
                    tab[index] = loHead;
                    if (hiHead != null) // (else is already treeified)
                        loHead.treeify(tab);
                }
            }
            if (hiHead != null) {
                if (hc <= UNTREEIFY_THRESHOLD)
                    tab[index + bit] = hiHead.untreeify(map);
                else {
                    tab[index + bit] = hiHead;
                    if (loHead != null)
                        hiHead.treeify(tab);
                }
            }
        }

        /* ------------------------------------------------------------ */
        // Red-black tree methods, all adapted from CLR

        static <K,V> TreeNode<K,V> rotateLeft(TreeNode<K,V> root,
                                              TreeNode<K,V> p) {
            TreeNode<K,V> r, pp, rl;
            if (p != null && (r = p.right) != null) {
                if ((rl = p.right = r.left) != null)
                    rl.parent = p;
                if ((pp = r.parent = p.parent) == null)
                    (root = r).red = false;
                else if (pp.left == p)
                    pp.left = r;
                else
                    pp.right = r;
                r.left = p;
                p.parent = r;
            }
            return root;
        }

        static <K,V> TreeNode<K,V> rotateRight(TreeNode<K,V> root,
                                               TreeNode<K,V> p) {
            TreeNode<K,V> l, pp, lr;
            if (p != null && (l = p.left) != null) {
                if ((lr = p.left = l.right) != null)
                    lr.parent = p;
                if ((pp = l.parent = p.parent) == null)
                    (root = l).red = false;
                else if (pp.right == p)
                    pp.right = l;
                else
                    pp.left = l;
                l.right = p;
                p.parent = l;
            }
            return root;
        }

        static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,
                                                    TreeNode<K,V> x) {
            x.red = true;
            for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                }
                else if (!xp.red || (xpp = xp.parent) == null)
                    return root;
                if (xp == (xppl = xpp.left)) {
                    if ((xppr = xpp.right) != null && xppr.red) {
                        xppr.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.right) {
                            root = rotateLeft(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateRight(root, xpp);
                            }
                        }
                    }
                }
                else {
                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.left) {
                            root = rotateRight(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }

        static <K,V> TreeNode<K,V> balanceDeletion(TreeNode<K,V> root,
                                                   TreeNode<K,V> x) {
            for (TreeNode<K,V> xp, xpl, xpr;;) {
                if (x == null || x == root)
                    return root;
                else if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                }
                else if (x.red) {
                    x.red = false;
                    return root;
                }
                else if ((xpl = xp.left) == x) {
                    if ((xpr = xp.right) != null && xpr.red) {
                        xpr.red = false;
                        xp.red = true;
                        root = rotateLeft(root, xp);
                        xpr = (xp = x.parent) == null ? null : xp.right;
                    }
                    if (xpr == null)
                        x = xp;
                    else {
                        TreeNode<K,V> sl = xpr.left, sr = xpr.right;
                        if ((sr == null || !sr.red) &&
                                (sl == null || !sl.red)) {
                            xpr.red = true;
                            x = xp;
                        }
                        else {
                            if (sr == null || !sr.red) {
                                if (sl != null)
                                    sl.red = false;
                                xpr.red = true;
                                root = rotateRight(root, xpr);
                                xpr = (xp = x.parent) == null ?
                                        null : xp.right;
                            }
                            if (xpr != null) {
                                xpr.red = (xp == null) ? false : xp.red;
                                if ((sr = xpr.right) != null)
                                    sr.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateLeft(root, xp);
                            }
                            x = root;
                        }
                    }
                }
                else { // symmetric
                    if (xpl != null && xpl.red) {
                        xpl.red = false;
                        xp.red = true;
                        root = rotateRight(root, xp);
                        xpl = (xp = x.parent) == null ? null : xp.left;
                    }
                    if (xpl == null)
                        x = xp;
                    else {
                        TreeNode<K,V> sl = xpl.left, sr = xpl.right;
                        if ((sl == null || !sl.red) &&
                                (sr == null || !sr.red)) {
                            xpl.red = true;
                            x = xp;
                        }
                        else {
                            if (sl == null || !sl.red) {
                                if (sr != null)
                                    sr.red = false;
                                xpl.red = true;
                                root = rotateLeft(root, xpl);
                                xpl = (xp = x.parent) == null ?
                                        null : xp.left;
                            }
                            if (xpl != null) {
                                xpl.red = (xp == null) ? false : xp.red;
                                if ((sl = xpl.left) != null)
                                    sl.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateRight(root, xp);
                            }
                            x = root;
                        }
                    }
                }
            }
        }

        /**
         * Recursive invariant check
         */
        static <K,V> boolean checkInvariants(TreeNode<K,V> t) {
            TreeNode<K,V> tp = t.parent, tl = t.left, tr = t.right,
                    tb = t.prev, tn = (TreeNode<K,V>)t.next;
            if (tb != null && tb.next != t)
                return false;
            if (tn != null && tn.prev != t)
                return false;
            if (tp != null && t != tp.left && t != tp.right)
                return false;
            if (tl != null && (tl.parent != t || tl.hash > t.hash))
                return false;
            if (tr != null && (tr.parent != t || tr.hash < t.hash))
                return false;
            if (t.red && tl != null && tl.red && tr != null && tr.red)
                return false;
            if (tl != null && !checkInvariants(tl))
                return false;
            if (tr != null && !checkInvariants(tr))
                return false;
            return true;
        }
    }




}
