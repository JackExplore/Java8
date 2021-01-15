package script.leetCode.T023;

/**
 * 合并 K 个升序链表
 *
 * 注意细节的处理
 *
 */
public class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode ret = new ListNode();
        ListNode pointer = ret;
        // 前置判断条件
        if (lists == null || lists.length < 1) {
            return null;
        }
        if (lists.length < 2) {
            return lists[0];
        }

        int cur = -1;
        while ((cur = minNode(lists)) != -1) {
            pointer.next = lists[cur];
            pointer = pointer.next;
            lists[cur] = lists[cur].next;
        }
        return ret.next;
    }

    public int minNode(ListNode[] list) {
        int temp = -1;
        int comp = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) {
                continue;
            }
            if (temp == -1) {           // !!!
                comp = list[i].val;
                temp = i;
            }
            if (comp > list[i].val) {   // !!!
                comp = list[i].val;
                temp = i;
            }
        }
        return temp;
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
