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
 * 允许 null 键值对
 * <p>
 * The <tt>HashMap</tt> class is roughly equivalent to <tt>Hashtable</tt>, except that it is unsynchronized and permits nulls.
 * 除了非同步控制和允许空值，HashMap 和 Hashtable 基本一致
 * <p>
 * Iteration over collection views requires time proportional to the "capacity" of the <tt>HashMap</tt> instance (the number of buckets) plus its size (the number
 * of key-value mappings).Thus, it's very important not to set the initial capacity too high (or the load factor too low) if iteration performance is important.
 * 迭代的效率和桶的数量和键值对长度成比例，所以，如果迭代效率很重要，那么，初始容量不要太高，加载因子不要太低。
 * <p>
 * <p>
 * An instance of HashMap has two parameters that affect its performance: initial capacity & load factor.
 * The capacity is the number of buckets in the hash table, and the initial capacity is simply the capacity at the time the hash table is created.
 * - 容量是指桶的数量
 * The load factor is a measure of how full the hash table is allowed to get before its capacity is automatically invreased.
 * - 加载因子是度量一个哈希表在达到多满时触发自动增长
 * When the number of entries in the hash table exceeds(chao guo) the product of the load factor and the current capacity,
 * the hash table is rehashed (that is, internal data structures are rebuilt)
 * so that the hash table has approximately twice the number of buckets.
 * - 如果键值对数量超过 加载因子和容量 的结果，将被重新哈希，然后，表的容量扩大至将近两倍
 * ? 这里是指加载因子和桶的数量吗
 * <p>
 * <p>
 *
 * As a general rule, the default load factor 0.75 offers a good tradeoff between time and space costs.
 *
 * If many mappings are to be stored in a HashMap instance, creating it with a sufficiently large capacity will
 * allow the mappings to be stored more efficiently than letting it perform automatic rehashing as needed to grow the table.
 *
 * 关于 HashMap 的同步操作：
 * Note that this implementation is not synchronized.
 * If... the map should be "wrapped" using the Collections.synchronizedMap method.
 * This is best done at creation time, to prevent accidental unsynchronized access to the map.
 * <pre>
 *     Map m = Collections.synchronizedMap(new HashMap(...));
 * </pre>
 *
 * The iterators 会因为结构变化导致快速失败，除非通过它自身的操作方法。
 * Note that the fail-fast behavior of an iterator cannot be guaranteed as it is.
 * Generally speaking, impossible to make any hard guarantees in the presence of unsynchronized concurrent modification.
 * 注意，迭代器的fail-fast行为不能得到保证，因为一般来说，在存在不同步的并发修改时，不可能做出任何硬性保证。
 * fail-fast 只是尽最大努力抛出 ConcurrentModificationException. 此行为仅用于检测 bug.
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
     * 默认的初始大小  - 必须是 2的次幂
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;     // 16 init capacity

    /**
     * 最大值 - 指定的值（必须是小于该值的 2 的次幂）
     */
    static final int MAXINUM_CAPACITY = 1 << 30;

    /**
     * 加载因子 - 当没有在构造函数指定的时候
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 阈值 - list -> tree
     * The bin count threshold for using a tree rather than list for a bin.
     * Bins are converted to trees when adding an element to a bin with at least this many nodes.
     * The value must be greater than 2 and should be at least 8 to mesh with assumptions in tree removal about conversion back to plain bins upon shrinkage.
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * 阈值 - tree->list
     * The bin count threshold for untreeifying a(split) bin during a resize operation.
     * Should be less than TREEIFY_THRESHOLD, and at most 6 to mesh with shrinkage detection under removal.
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * The smallest table capacity for which bins my be treeified.
     * ? (Otherwise the table is resized if too many nodes in a bin)
     * ? Should be at least 4*TREEIFY_THRESHOLD to avoid conflicts between resizing and treeification thresholds.
     * ? 这是为什么
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
     * 之所以这样计算 hash 值的原因是，扩展 key.hashcode 的高位到低位。
     * 为了避免碰撞
     * 许多 float 类型的 key 总是在小表上连续不断的冲突，所以我们将高位的影响下移。
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 对比 x 的 Class 类型，如果实现了Comparable接口，否则返回null
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
                            ((p = (ParameterizedType) t).getRawType() == Comparable.class) && // 是否继承了 Comparable 接口？
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
        return (x == null || x.getClass() != kc ? 0 : ((Comparable) k).compareTo(x)); // 这个表达式运算顺序有待验证
//        return (x == null || x.getClass() != kc) ? 0 : ((Comparable)k).compareTo(x); // 应该是这样
    }

    /**
     * Returns a power of two size for the given target capacity.
     * ?
     * 功能：大于等于输入参数且最接近 2的次幂 的数
     * 学习：效率非常高的算法，好厉害
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;    // 为了使目标值 >= 原值
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
     * 在某些操作中，我们也允许长度为 0，以允许当前并不需要的引导机制?
     * ?
     * 这就是那个桶
     */
    transient Node<K, V>[] table;

    /**
     * Holds cached entrySet().
     * Note that AbstractMap fields are used for keySet() and values().
     * 缓存的 Entry /?
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
     * 那些改变映射数量或者以其他方式修改其内部结构的次数
     * 此字段用于集合视图上的快速失败 - See ConcurrentModificationException
     */
    transient int modCount;

    /**
     * The next size value at which to resize (capacity * load factor)
     * 下次进行 resize 的时候的阈值
     * @Serial
     * The javadoc description is true upon serialization. Additionally, if the table array has not been allocated, this field holds the initial array capacity, or zero signifying DEFAULT_INITIAL_CAPACITY.
     * javadoc描述在序列化时为true。此外，如果尚未分配表数组，则此字段保留初始数组容量，或零，表示默认的"初始"容量
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
        // ? 为什么初始值直接设置了阈值，不应该是 init*loadfactor 吗
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
     * HashMap是使用默认加载因子（0.75）和足以在指定映射中保存映射的初始容量创建的。
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
        if(s > 0){                                              // 前提是传入 map 的大小不为 0
            if(table == null){  // pre-size                     // 说明书拷贝函数来调用 putMapEntries，或者构造后还没放过任何元素
                float ft = ((float)s / loadFactor) + 1.0f;      // 使得 size 刚好不大于阈值的一个量，并向上取整
                int t = ((ft < (float)MAXINUM_CAPACITY) ? (int)ft : MAXINUM_CAPACITY);
                // 虽然上面一顿操作，但只有在算出来的容量 t > 当前暂存的容量，才或重新计算新容量
                if(t > threshold){
                    threshold = tableSizeFor(t);
                }
            }else if(s > threshold){    // 说明 table 已经初始化过了；判断其size，是否需要 resize()
                resize();
            }

            // 循环里的 putVal 可能也会触发 resize
            for(Map.Entry<? extends K, ? extends V> e : m.entrySet()){
                // Entry 泛型类对象，只能使用get类型的函数
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
     * 针对 key 为 null 的情况，使用 containsKey() 可以分辨
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
     * 获取 node - 通过 hash & key
     * @param hash  hash for key
     * @param key   the key
     * @return  the node, or null if none
     */
    final Node<K,V> getNode(int hash, Object key){
        Node<K,V>[] tab;
        Node<K,V> first, e;
        int n;
        K k;

        if((tab = table) != null &&                     // 获取 hash table
                (n = tab.length) > 0 &&                 // 有元素
                (first = tab[(n-1) & hash])!= null){    // 当(n - 1) 与 hash 做与运算时，会保留hash中 后 x 位的 1，这样就保证了索引值 不会超出数组长度。
            // 定位到某一个桶
            if(first.hash == hash &&                    // always check the first node
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;

            if((e=first.next) != null){                 // 存在 next 时，继续往下找
                if(first instanceof TreeNode)           // 1 如果是树形结构
                    return ((TreeNode<K,V>first).getTreeNode(hash, key));

                do {                                    // 2 如果是链表结构
                    /**
                     * 两个条件:
                     * A/ hash 值相等
                     * B/ 引用相同  或者  equals->true
                     * 所以说，先定位到桶，然后hash值相等，最后比较 引用 或者 equals相等
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
        // 判断的是 node 是否为 null，所以可以判断 node.key = null 的情况，在 0 号元素
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
     * @return the previous value associated with key,  返回原值
     *         or null if there was no mapping for key. 不存在key，返回null
     *         A null return can also indicate that the map previously associated null with key. 原值为 null
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
     * @param onlyIfAbsent  if true, don't change existing value  不进行覆盖操作
     * @param evict if false, the  table is in creation mode.
     * @return  previous value, or null if none
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict){
        Node<K,V>[] tab;        // hashmap 的 Node 数组
        Node<K,V> p;            // 要操作的 node
        int n, i;               // n 为当前hashmap的node数组的长度，i 为操作的数组下标
        if((tab = table) == null || (n=tab.length) == 0){   // 存储元素的 table 为空时，进行必要字段的初始化 resize()
            n = (tab = resize()).length;
        }

        if((p = tab[i=(n-1) & hash]) == null){              // 当此桶为空时，增加新Node
            tab[i] = newNode(hash, key, value, null);
        }else {                                              // 不为空时的操作，进入到桶内
            Node<K, V> e;
            K k;

            // 1 如果新插入的节点和 table 中的 p 结点的 hash 值，key 值相同的话
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;                                      // 定位到这个node，并将 e 指向它
                // 2 当前是红黑树结点的话，进行红黑树插入
            } else if (p instanceof TreeNode) {
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
                // 3 当前不是红黑树
            } else {
                for (int binCount = 0; ; ++binCount) {                // 逐个对比
                    // Q:null
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1)       // -1 for first 链表长度大于8时，将链表转换为红黑树：8 - 1 + 1(新增) + headP = 9个元素?
                            treeifyBin(tab, hash);                  // 树化
                        break;
                    }
                    // not null
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        break;

                    p = e;  // 进入下一次迭代
                }
            }

            // A 如果存在这个映射就覆盖
            if (e != null) {  // existing mapping for key
                V oldValue = e.value;
                // 判断是否允许覆盖，并且 value 是否为空
                if (!onlyIfAbsent || oldValue == null) {
                    e.value = value;
                }
                afterNodeAccess(e); // 回调以允许 LinkedHashMap 后置操作 ?
                return oldValue;
            }// 这个操作并没有更改结构 - []Nodes
        }

        // B 如果不存在, 参考 Q 处
        ++modCount;                // 更改操作次数
        if (++size > threshold) {     // 大于临界值，阈值 ? size > 16 * .75 也就是说，默认 >12 元素后，进行数组扩容
            // 将数组设置为原来的 2 倍,并将原来的数组元素放到新数组中
            // 因为有链表，红黑树等，因此还要调整他们
            resize();
        }
        afterNodeInsertion(evict);  // 回调以允许 LinkedHashMap 后置操作 ?
        return null;

    }


    /**
     * Initializes or doubles table size.
     * 初始化 或 加倍表大小。
     * If null, allocates in accord with initial capacity target held in field threshold.
     *
     * Otherwise, because we are using power-of-two expansion, the elements from each bin must
     * either stay at same index, or move with a power of two offset in the new table.
     * 否则，因为我们使用的是二次幂，每个桶中的元素，要么保持在相同的下标，要么在新表中以 2 的次幂偏移量移动。
     *
     */
    final Node<K,V>[] resize(){
        Node<K,V>[] oldTab = table;
        // 现在的 hash 表容量，第一次初始化时为 0
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        // 现在的扩容临界值
        int oldThr = threshold;

        int newCap, newThr = 0;
        // hash 表容量不为 0 时
        if(oldCap > 0){
            /**
             * 如果 hash 表容量已达到最大临界值，则返回原数组，并且扩容临界值保持不变，
             * 否则，数组扩容一倍且扩容后的表不能大于限制值，
             */
            if (oldCap >= MAXINUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXINUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY) {
                newThr = oldThr << 1;   // double threshold 阈值翻倍
            }
        }else if(oldThr > 0){
            newCap = oldThr;        // 容量用阈值？
        }else{
            newCap = DEFAULT_INITIAL_CAPACITY;      // 第一次创建，使用默认值生成相关参数
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }

        // 第一次扩容初始化阈值?
        if(newThr == 0){
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXINUM_CAPACITY && ft < (float)MAXINUM_CAPACITY ? (int) ft : Integer.MAX_VALUE);
        }
        // 更新扩容临界值
        threshold = newThr;

        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        // 已存在 hash 表
        if(oldTab != null){
            // 遍历 hahs 表中的每个桶
            for (int j = 0; j < oldCap; ++j) {
                // 临时 node 变量，指向旧桶中的结点元素
                Node<K,V> e;
                // 如果旧的 hash 表的当前桶位置存在结点，将赋值给 e
                if((e=oldTab[j]) != null){
                    // 另旧桶置为 null
                    oldTab[j] = null;
                    if (e.next == null) {                                    // 如果取出来的结点不存在下一个元素，则重新计算对应的新 hash 桶的位置
                        newTab[e.hash & (newCap - 1)] = e;
                    } else if (e instanceof TreeNode) {                       // 红黑树 ?
                        ((TreeNode < K, V > e).split(this, newTab, j, oldCap));
                    } else {                                                 // 链表
                        Node<K, V> loHead = null, loTail = null;            // 原桶位置
                        Node<K, V> hiHead = null, hiTail = null;            // 新桶位置，即扩容一倍后的位置
                        Node<K, V> next;    // 下一个结点
                        do {
                            next = e.next;  // 指向下一个结点
                            if ((e.hash & oldCap) == 0) {                   // 判断当前结点的 hash 值的 (放入时代码：tab[i=(n-1) & hash])
                                                                            // 比hash表容量高 1 位的二进制位是否为 1，
                                                                            // 如果为0，结点位置保持原桶，如果为1，到新桶
                                if (loTail == null) {           // 原桶位置的链表尾
                                    loHead = e;                 // 将当前结点设置为链表头
                                } else {
                                    loTail.next = e;
                                }
                                loTail = e;                     // 设置当前尾结点
                            } else {
                                if (hiTail == null) {
                                    hiHead = e;
                                } else {
                                    hiTail.next = e;
                                }
                                hiTail = e;
                            }
                        } while ((e = next) != null);

                        if(loTail != null){                 // 原位置存在链表
                            loTail.next = null;
                            newTab[j] = loHead;             // 原 hash 位置
                        }

                        if(hiTail != null){                 // 新桶位置存在链表
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;    // 当前 桶位置 + 旧的hash 表容量
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
     * 把容器里的元素变成树结构。
     *
     * 当链表长度大于等于 9 ，会调用该方法处理
     *
     * @param tab
     * @param hash
     */
    final void treeifyBin(Node<K,V>[] tab, int hash){
        int n, index;
        Node<K,V> e;
        /**
         * 如果桶数组为空  或者  桶数组长度 小于  树结构化的最小限制 -
         * MIN_TREEFIY_CAPACITY 默认值是 64，对于这个值可以理解为：如果桶数组长度小于这个值，没有必要去进行结构转换
         * 当一个数组位置上集中了多个键值对，那是因为这些 key 的 hash 值和数组长度 与运算 后结果相同，而并不是这些 key 的 hash 值相同。
         * 因为 hash 值相同的概率不高，所以可以通过扩容的方式，来使得最终这些 key 的 hash 值在和新的数组长度取模之后，拆分到多个数组位置上。
         */
        if(tab == null || (n=tab.length) < MIN_TREEIFY_CAPACITY){
            resize();

        // 如果大于阈值，那么就有必要进行结构转换了，根据 hash 值和数组长度进行取模运算后，得到链表的首结点
        } else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<K,V> hd = null, tl = null;     // 定义首/尾节点
            do{
                // 将该结点转换为树结点
                TreeNode<K,V> p = replacementTreeNode(e, null);
                if(tl == null){     // 如果尾结点为空，说明还没有根结点
                    hd = p;         // 首结点（根结点）指向当前结点
                }else{              // 尾结点不为空，以下两行是一个双向链表结构
                    p.prev = tl;    // 当前树结点的前一个结点指向尾结点
                    tl.next = p;    // 尾结点的 后一个结点指向当前结点
                }
                tl = p;             // 当前结点设置为尾结点
            }while((e=e.next) != null); // 继续遍历链表

            /**
             * 到目前为止，也只是把 Node 对象转换成了 TreeNode 对象，把单项链表转换成了双向链表
             */

            // 把转换之后的双向链表，替换原来位置上的单项链表
            if((tab[index] = hd) !=null){
                hd.treeify(tab);        // 此处单独解析
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
        // 当 table 不为空，并且 hash 对应的桶不为空时
        if ((tab = table) != null && (n = tab.length) > 0 && (p = tab[index = (n - 1) & hash]) != null) {
            Node<K, V> node = null, e;
            K k;
            V v;
            // 当桶中的 头结点 就是要删除的结点
            if(p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                // 用 node 记录要删除的头结点
                node = p;
            // 头结点不是要删除的结点，并且头结点后还有结点
            }else if((e = p.next) != null){
                // 头结点 为 树结点，则进入树查找要删除的结点
                if(p instanceof TreeNode){
                    node = ((TreeNode<K,V>)p).getTreeNode(hash, key);
                // 头结点 为 链表结点
                }else{
                    // 遍历链表
                    do{
                        // hash 值相等 && （key地址相等  或者  equals）
                        if(e.hash == hash && ((k=e.key) == key || (key != null && key.equals(k)))){
                            // node 记录要删除的结点
                            node = e;
                            break;
                        }
                        // p 保存当前遍历到的结点
                        p = e;
                    }while((e = e.next) != null);
                }
            }
            // 要找的结点不为空，并且。。。 matchValue ?
            if (node != null && (!matchValue || (v = node.value) == value || (value != null && value.equals(v)))) {
                // 在树种删除结点
                if(node instanceof TreeNode){
                    ((TreeNode<K,V>) node).removeTreeNode(this, tab, movable);
                // 删除的是 头结点
                }else if(node == p){
                    tab[index] = node.next;
                // 不是头结点，指向下一个
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
     * 集合由 map 支持，所以 map 的更改将反映在set中，反之亦然。
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

        // 对每个 node 进行 accept 操作
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
     * 参考 keySet
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
                if(modCount != mc){             // 并发警告
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
     * 重写 JDK 8 扩展方法
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
     * 调用此方法时，实现 BiConsumer 接口重写 accept(Object o, Object o2) 方法，可根据自己的实现对 map 所有数据进行处理
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

    /*调用此方法时重写BiFunction的Object apply(Object o, Object o2)方法，其中o为key，o2为value，根据重写方法逻辑进行重新赋值*/
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
     * 返回这个实例的浅拷贝：键和值本身没有被克隆，仍然是原引用
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
            // 当前桶里面找过了，去下一个桶里找
            if ((next = (current = e).next) == null && (t = table) != null) {
                do {} while (index < t.length && (next = t[index++]) == null);
            }
            return e;
        }

        /**
         * 连续 2 次 remove 会报错？
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
