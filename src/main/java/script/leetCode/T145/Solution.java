package script.leetCode.T145;

import java.util.ArrayList;
import java.util.List;

/**
 * ¶þ²æÊ÷ - ºóÐò±éÀú
 */
public class Solution {

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        postTravel(root, list);
        return list;
    }
    public void postTravel(TreeNode root, List<Integer> list){
        if(root != null){
            postTravel(root.left, list);
            postTravel(root.right, list);
            list.add(root.val);
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
