package script.leetCode.Offer009;

import java.util.Stack;

/**
 * ������ջʵ��һ������
 */
public class CQueue {

    Stack<Integer> first = new Stack<>();
    Stack<Integer> second = new Stack<>();

    public CQueue() {

    }

    public void appendTail(int value) {
        while(!second.isEmpty()){
            first.push(second.pop());
        }
        first.push(value);
    }

    public int deleteHead() {
        if(first.isEmpty() && second.isEmpty()){
            return -1;
        }
        while(!first.isEmpty()){
            second.push(first.pop());
        }
        return second.pop();
    }
}
