package script.leetCode.Offer024;

/**
 * 链表反转
 *
 * 一次性成功的反转链表@！
 *
 * 1、首先判断结点是否为空，以及是否单个结点，直接返回 head，不需要反转；
 *
 * 2、旧链表
 *          设置两个指针，分别表示转换结点和剩余链表；
 *    新链表
 *          设置头指针，每次往前移动；
 *
 * 3、剩余链表结点指针为空，返回新的头结点；
 *
 */
public class Solution {

    public ListNode reverseList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode oldPointer, oldCur, newHead;
        oldPointer = oldCur = head;
        newHead = null;

        while (oldPointer != null) {
            oldCur = oldPointer.next;

            oldPointer.next = newHead;
            newHead = oldPointer;

            oldPointer = oldCur;
        }

        return newHead;

    }

    public static void main(String[] args) {
        Solution s = new Solution();
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;

        printLink(l1);
        ListNode re = s.reverseList(l1);

        printLink(re);

    }

    public static void printLink(ListNode ll) {

        while (ll != null) {
            System.out.print(ll.val + " ");
            ll = ll.next;
        }
        System.out.println();
    }
}


class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
