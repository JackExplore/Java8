package script.leetCode.T007;

/**
 * 整数反转 Pro
 */
public class Solution {

    public int reversePro(int x) {
        int ans = 0;
        int number = x;
        while (number != 0) {
            if (ans * 10 / 10 != ans) {     // 整数越界的判定方法
                ans = 0;
                break;
            }
            ans = ans * 10 + number % 10;
            number = number / 10;
        }
        return ans;
    }

    public int reverse(int x) {

        int flag = x > 0 ? 1 : -1;

        String s = String.valueOf(Math.abs(x));
        StringBuffer sb = new StringBuffer();
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        int newValue = 0;
        try {
            newValue = Integer.parseInt(sb.toString());
        } catch (Exception e) {
            return 0;
        }
        return newValue * flag;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.reverse(123));
    }
}
