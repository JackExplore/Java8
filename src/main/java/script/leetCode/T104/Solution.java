package script.leetCode.T104;

/**
 * 最大树深度
 *
 */
public class Solution {

    int deep = 0;
    int deepConst = 0;
    public int maxDepth(TreeNode root) {
        maxD(root);
        return deepConst;
    }

    public void maxD(TreeNode root){
        if(root != null){
            deep++;
            maxD(root.left);
            maxD(root.right);
            if(deepConst < deep){
                deepConst = deep;
            }
            deep--;
        }
    }

    public static void main(String[] args) {
        TreeNode left = new TreeNode();
        TreeNode right = new TreeNode(2);
        TreeNode root = new TreeNode(1, left, right);

        Solution s = new Solution();
        System.out.println(s.maxDepth(root));


    }

}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
