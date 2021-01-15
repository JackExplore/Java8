package com.java.util.concurrent;


import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import sun.misc.Unsafe;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public class AQST {



    static final class Node {
        static final Node SHARED = new Node();
        static final Node EXCLUSIVE = new Node();

        static final int CANCELLED = 1;
        static final int SIGNAL = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        volatile Node prev;
        volatile Node next;
        volatile Thread thread;
        Node nextWaiter;

        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null) {
                throw new NullPointerException();
            } else {
                return p;
            }
        }


        Node(){     // Used to establish initial head or SHARED marker
        }

        Node(Thread thread, Node mode){         // Used by addWaiter
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus){    // Used by Condition
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }

    private transient volatile Node head;

    private transient volatile Node tail;

    private volatile int state;

    protected final int getState(){
        return state;
    }

    protected final void setState(int newState){
        state = newState;
    }

    protected final boolean compareAndSetState(int expect, int update){
//        new unsafe()
        return false;
    }





    public static void main(String[] args) {

        ReentrantLock reentrantLock = new ReentrantLock();
    }


    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;
    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (AQST.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (AQST.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (AQST.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                    (AQST.Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                    (AQST.Node.class.getDeclaredField("next"));

        } catch (Exception ex) { throw new Error(ex); }
    }

    private final boolean compareAndSetHead(Node update){
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    private final boolean compareAndSetTail(Node expect, Node update){
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }
    /**
     * CAS waitStatus field of a node.
     */
    private static final boolean compareAndSetWaitStatus(AQST.Node node,
                                                         int expect,
                                                         int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset,
                expect, update);
    }

    /**
     * CAS next field of a node.
     */
    private static final boolean compareAndSetNext(AQST.Node node,
                                                   AQST.Node expect,
                                                   AQST.Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }
}
