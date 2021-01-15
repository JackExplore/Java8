package script.leetCode.T704;

public class Solution {

    public int search(int[] nums, int target) {

        if (nums == null || nums.length < 1) {
            return -1;
        }

        int s = 0, t = nums.length - 1;
        while (s <= t) {
            int mid = (s + t) / 2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] > target){
                t = mid - 1;
            }else {
                s = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = {-1,0,3,5,9,12};
        int target = 6;
        int search = s.search(nums, target);
        System.out.println(search);
    }
}
