package script.leetCode.T070;

/**
 * ÅÀÂ¥Ìİ
 */
public class Solution {

    public int climbStairs(int n) {

        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    public int climbStairsT(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int[] sta = new int[n];
        sta[0] = 1;
        sta[1] = 2;
        for (int i = 2; i < n; i++) {
            sta[i] = sta[i - 1] + sta[i - 2];
        }
        return sta[n-1];
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.climbStairsT(5));
    }

}
