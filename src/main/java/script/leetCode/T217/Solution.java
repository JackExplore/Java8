package script.leetCode.T217;

import java.util.HashMap;

/**
 * �Ƿ�����ظ�Ԫ��
 */
public class Solution {
    public boolean containsDuplicate(int[] nums) {

        if(nums == null || nums.length < 1){
            return false;
        }
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer put = hashMap.put(nums[i], nums[i]);
            if(put != null){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] arr = new int[]{1,2,3,1};
        System.out.println(s.containsDuplicate(arr));
    }
}
