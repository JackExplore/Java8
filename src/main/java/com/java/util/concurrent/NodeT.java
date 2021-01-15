package com.java.util.concurrent;

public class NodeT {

    static final class Node {

        /**
         * Marker to indicate a node is waiting in shared mode
         */
        static final Node SHARED = new Node();
        /**
         * Marder to indicate a node is waiting in exclusive mode
         */
        static final Node EXCLUSIVE = null;

        /**
         * waitStatus value to indicate thread ...
         */
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

        Node(){}

        Node(Thread thread, Node mode){
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus){
            this.waitStatus = waitStatus;
            this.thread = thread;
        }

    }
}
