package script.leetCode.T027;

/**
 * ÒÆ³ıÖ¸¶¨ÔªËØ
 */
public class Solution {

    public int removeElement(int[] nums, int val) {
        int p = 0, q = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == val){
                q++;
            }else{
                nums[p++] = nums[q++];
                count++;
            }
        }
        return count;
    }
}
