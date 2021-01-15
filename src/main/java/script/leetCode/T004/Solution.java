package script.leetCode.T004;

/**
 * 两个正序数组中的中位数
 * 
 */
public class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int[] nums = new int[nums1.length + nums2.length];

        int i = 0, j = 0, k = 0;
        for (; i < nums1.length && j < nums2.length; ) {
            if (nums1[i] < nums2[j]) {
                nums[k] = nums1[i];
                i++;
            } else {
                nums[k] = nums2[j];
                j++;
            }
            k++;
        }

        while (i < nums1.length) {
            nums[k] = nums1[i];
            i++;
            k++;
        }
        while (j < nums2.length) {
            nums[k] = nums2[j];
            j++;
            k++;
        }

        if (nums.length % 2 == 0) {     // 这里特别注意
            return (nums[nums.length / 2 - 1] + nums[nums.length / 2 ]) / 2.0;
        } else {
            return nums[nums.length / 2];
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2};
        int[] nums2 = {3, 4};
        Solution s = new Solution();
        System.out.println(s.findMedianSortedArrays(nums1, nums2));
    }

}
