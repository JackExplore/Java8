package script.leetCode.T144;

import java.util.ArrayList;
import java.util.List;

/**
 * ¶þ²æÊ÷
 * Ç°Ðò±éÀú
 */
public class Solution {


    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        preTravel(root, list);
        return list;
    }
    public void preTravel(TreeNode root, List<Integer> list){
        if(root != null){
            list.add(root.val);
            preTravel(root.left, list);
            preTravel(root.right, list);
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
