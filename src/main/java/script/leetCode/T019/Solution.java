package script.leetCode.T019;

/**
 * 删除链表的倒数第 N 个结点
 *
 * 可见，在对链表操作的时候，头结点要单独处理，特别修改连接关系时
 */

class ListNode {
    int val;
    ListNode next;
    ListNode() {
    }
    ListNode(int val) {
        this.val = val;
    }
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode p, q;
        p = q = new ListNode();
        p.next = head;

        boolean start = false;
        int count = 0;
        while (p != null) {
            p = p.next;
            count++;
            if (start && p != null) {
                q = q.next;
            }
            if (count == n) {
                start = true;
            }
        }
        if (start) {
            if(q.next == head){         // ! 这关键的一步
                return head.next;
            }
            q.next = q.next.next;
        }
        return head;
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
        s.removeNthFromEnd(l1, 2);
        printLink(l1);

        ListNode s1 = new ListNode(1);
        ListNode s2 = new ListNode(2);
        s1.next = s2;
        printLink(s1);
        s1 = s.removeNthFromEnd(s1, 1);
        printLink(s1);

    }

    public static void printLink(ListNode ll) {

        while (ll != null) {
            System.out.print(ll.val + " ");
            ll = ll.next;
        }
        System.out.println();
    }
}
