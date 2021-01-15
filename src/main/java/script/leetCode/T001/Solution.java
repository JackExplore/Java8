package script.leetCode.T001;

import java.util.HashMap;

/**
 * 两数之和
 */
public class Solution {
    public int[] twoSum(int[] nums, int target) {

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((nums[i] + nums[j]) == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[2];
    }

    public static void main(String[] args) {

    }
}
