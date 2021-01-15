package script.leetCode.T035;

public class Solution {
    public int searchInsert(int[] nums, int target) {

        if(nums == null){
            return 0;
        }

        int i = 0;
/*        for (i = 0; i < nums.length; i++) {
            if(nums[i] >= target){
                break;
            }
        }*/
        for (i = 0; i < nums.length && nums[i] < target; i++);
        return i;

    }
}
