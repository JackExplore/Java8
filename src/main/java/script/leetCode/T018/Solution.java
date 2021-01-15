package script.leetCode.T018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 四数之和
 * 注意细节之处的处理过程
 */
public class Solution {


    public List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return result;
        }
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        // 1 !
        for (int i = 0; i < nums.length - 3; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) {
//                i++;        // 注意这里 i++ 是多余的！
                continue;
            }
            // 2 !
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int m = j + 1;
                int n = nums.length - 1;
                while (m < n) {
                    int sum = nums[i] + nums[j] + nums[m] + nums[n];
                    if (sum == target) {
                        List<Integer> item = new ArrayList<>();
                        item.add(nums[i]);
                        item.add(nums[j]);
                        item.add(nums[m]);
                        item.add(nums[n]);
                        result.add(item);
                        System.out.println(nums[i] + " " + nums[j] + " " + nums[m] + " " + nums[n]);

                        while (m < n && (nums[m] == nums[m + 1])) {
                            m++;
                        }

                        while (m < n && (nums[n] == nums[n - 1])) {
                            n--;
                        }
                        m++;
                        n--;
                    } else if (sum > target) {
                        n--;
                    } else if (sum < target) {
                        m++;
                    }

                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        int[] nums = {-3, -4, -5, 0, -5, -2, 5, 2, -3};
        int target = 3;
        s.fourSum(nums, target);
    }
}
