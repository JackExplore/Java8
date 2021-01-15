package script.leetCode.T094;

import java.util.ArrayList;
import java.util.List;

/**
 * ¶þ²æÊ÷ - ÖÐÐò±éÀú
 *
 */
public class Solution {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inTravel(root, list);
        return list;
    }
    public void inTravel(TreeNode root, List<Integer> list){
        if(root != null){
            inTravel(root.left, list);
            list.add(root.val);
            inTravel(root.right, list);
        }
    }

}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) {
        val = x;
    }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
