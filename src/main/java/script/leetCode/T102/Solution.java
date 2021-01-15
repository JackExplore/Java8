package script.leetCode.T102;

import java.util.*;

/**
 * 二叉树层序遍历
 * <p>
 * Key : 记录了每一层的结点数
 */
public class Solution {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> bin = new LinkedList<>();
        bin.add(root);

        while (!bin.isEmpty()) {
            List<Integer> ret = new LinkedList<>();
            int retSize = bin.size();               // !!!!!!!
            for (int i = 0; i < retSize; i++) {
                TreeNode poll = bin.poll();
                ret.add(poll.val);
                if (poll.left != null) {
                    bin.add(poll.left);
                }
                if (poll.right != null) {
                    bin.add(poll.right);
                }
            }
            result.add(ret);
        }

        return result;
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
