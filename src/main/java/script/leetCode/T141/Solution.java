package script.leetCode.T141;


import java.util.HashSet;

/**
 * 链表是否有环
 *
 */
public class Solution {

    /**
     * 快慢指针
     * @param head
     * @return
     */
    public boolean hasCyclePro(ListNode head){
        if(head == null || head.next == null){
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while(slow != fast){
            if(fast == null || fast.next == null){
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * HashSet 解题
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> hashSet = new HashSet<>();
        ListNode pointer = head;
        while(pointer != null){
            if(!hashSet.add(pointer)){
               return true;
            }
            pointer = pointer.next;
        }
        return false;
    }

    public static void main(String[] args) {
        script.leetCode.T014.Solution s = new script.leetCode.T014.Solution();
        String[] ss = {"ab","a"};
        System.out.println(s.longestCommonPrefix(ss));
    }
}





class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}
