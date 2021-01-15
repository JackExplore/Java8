package script.leetCode.T011;

/**
 * 盛水最多的容器
 */
public class Solution {

    public int maxArea(int[] height) {

        if (height == null || height.length < 2) {
            return 0;
        }

        int startPos = 0;
        int endPos = height.length - 1;
        int area = 0;

        for (int i = 0; i < height.length; i++) {
            int minHeight = height[startPos] < height[endPos] ? height[startPos] : height[endPos];
            int tempArea = minHeight * (endPos - startPos);
            if(area < tempArea){
                area = tempArea;
            }
            if(height[startPos] < height[endPos]){
                startPos++;
            }else {
                endPos--;
            }

        }

        return area;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = {1,8,6,2,5,4,8,3,7};
        System.out.println(s.maxArea(a));
    }
}
