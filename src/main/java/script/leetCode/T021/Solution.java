package script.leetCode.T021;

/**
 * 合并两个有序链表
 */
public class Solution {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode cur1 = l1;
        ListNode cur2 = l2;
        ListNode head = new ListNode();
        ListNode cur = head;

        if(cur1 == null && cur2 == null){
            return null;
        }
        if(cur1 == null && cur2 != null){
            return cur2;
        }
        if(cur1 != null && cur2 == null){
            return cur1;
        }

        while(cur1 != null && cur2 != null){
            if(cur1.val < cur2.val){
                cur.next = cur1;
                cur = cur.next;
                cur1 = cur1.next;
            }else{
                cur.next = cur2;
                cur = cur.next;
                cur2 = cur2.next;
            }
        }
        while(cur1 != null){
            cur.next = cur1;
            cur = cur.next;
            cur1 = cur1.next;
        }
        while(cur2 != null){
            cur.next = cur2;
            cur = cur.next;
            cur2 = cur2.next;
        }
        head = head.next;
        return head;
    }


    /**
     * ?
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoListsPro(ListNode l1, ListNode l2){
        if(l1 == null){
            return l2;
        }else if(l2 == null){
            return l1;
        }else if(l1.val < l2.val){
            l1.next = mergeTwoListsPro(l1.next, l2);
            return l1;
        }else{
            l2.next = mergeTwoListsPro(l1, l2.next);
            return l2;
        }
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
