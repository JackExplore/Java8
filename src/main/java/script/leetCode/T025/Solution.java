package script.leetCode.T025;

public class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {

        ListNode HEAD = new ListNode();
        HEAD.next = head;
        ListNode pointer = HEAD;
        ListNode pre = HEAD;
        int count = 0;

        while (pointer.next != null) {
            count++;
            pointer = pointer.next;

            if (count % k == 0) {
                ListNode temp = pointer.next;

                /**
                 * pre -> ! n1 -> n2 -> ... -> pointer ! -> temp
                 */
//                reverse0(pre.next, pointer);

            }
        }
        return null;
    }

    public ListNode reverse0(ListNode head) {

        ListNode h = null;
        return h;

    }


}


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


