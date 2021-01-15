package script.leetCode.T015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三个数之和
 * <p>
 * 请你找出所有满足条件且不重复的三元组
 * 不重复？
 */
public class Solution {

    public List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }

        // 首先进行排序
        Arrays.sort(nums);
//        System.out.println(Arrays.toString(nums));
        for (int i = 0; i < nums.length - 2; i++) {

            // 如果 target != 0 ，则不应该再判断！！！
            if(nums[i] > 0){
                break;
            }
            if ((i - 1 >= 0) && (nums[i] == nums[i - 1])) {
                continue;
            }
            int pointer = nums[i];
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                if (pointer + nums[j] + nums[k] == 0) {
                    List<Integer> item = new ArrayList<>();
                    item.add(pointer);
                    item.add(nums[j]);
                    item.add(nums[k]);
                    result.add(item);
//                    System.out.println(pointer + " " + nums[j] + " " + nums[k]);

                    while (j < k && (nums[j] == nums[j + 1])) {
                        j++;
                    }
                    while (j < k && (nums[k] == nums[k - 1])) {
                        k--;
                    }
                    j++;
                    k--;

                } else if (pointer + nums[j] + nums[k] > 0) {
                    k--;
                } else if (pointer + nums[j] + nums[k] < 0) {
                    j++;
                }
            }
        }

        return result;
    }


    public static void main(String[] args) {
        Solution s = new Solution();

        int[] nums = {-1, 0, 1, 0};

        s.threeSum(nums);
    }
}
