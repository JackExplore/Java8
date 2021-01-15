package script.leetCode.Offer024;

/**
 * ����ת
 *
 * һ���Գɹ��ķ�ת����@��
 *
 * 1�������жϽ���Ƿ�Ϊ�գ��Լ��Ƿ񵥸���㣬ֱ�ӷ��� head������Ҫ��ת��
 *
 * 2��������
 *          ��������ָ�룬�ֱ��ʾת������ʣ������
 *    ������
 *          ����ͷָ�룬ÿ����ǰ�ƶ���
 *
 * 3��ʣ��������ָ��Ϊ�գ������µ�ͷ��㣻
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
