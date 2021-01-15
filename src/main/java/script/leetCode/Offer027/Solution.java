package script.leetCode.Offer027;

/**
 * ¶þ²æÊ÷µÄ¾µÏñ
 *
 */
public class Solution {

    public TreeNode mirrorTree(TreeNode root) {

        reverseTree(root);

        return root;
    }

    public void reverseTree(TreeNode node){
        if(node == null){
            return ;
        }
        TreeNode temp;
        temp = node.left;
        node.left = node.right;
        node.right = temp;

        reverseTree(node.left);
        reverseTree(node.right);
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}
