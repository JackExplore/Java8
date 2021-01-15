package script.leetCode.T055;

/**
 * ÌøÔ¾ÓÎÏ·
 * Ì°ĞÄËã·¨
 */
public class Solution {


    public boolean canJump(int[] nums) {
        int len = nums.length;
        int canGo = nums[0];
        for (int i = 0; i <= canGo; i++) {
            int step = i + nums[i];
            if (step >= len - 1) {
                return true;
            }
            if (canGo < step) {
                canGo = step;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        boolean can = s.canJump(new int[]{2, 3, 1, 1, 4});
        System.out.println(can);
    }
}
