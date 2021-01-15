package script.leetCode.T026;

/**
 * 删除排序数组中的重复项
 *
 */
public class Solution {
    public int removeDuplicates(int[] nums) {
        if(nums == null){
            return 0;
        }
        if(nums.length < 1){
            return 0;
        }

        int temp = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if(temp != nums[i]){
                temp = nums[i];
                nums[count++] = temp;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] arr = {0,0,1,1,1,2,2,3,3,4};
        System.out.println(s.removeDuplicates(arr));
    }
}
