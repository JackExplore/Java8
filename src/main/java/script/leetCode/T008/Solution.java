package script.leetCode.T008;

import java.awt.print.PrinterGraphics;
import java.util.HashMap;

public class Solution {

    HashMap<Character, Character> hashMap = new HashMap();
    HashMap<Character, Character> hashMapFlag = new HashMap();

    public void init() {
        hashMapFlag.put('-', '-');
        hashMapFlag.put('+', '+');
        hashMap.put('1', '1');
        hashMap.put('2', '2');
        hashMap.put('3', '3');
        hashMap.put('4', '4');
        hashMap.put('5', '5');
        hashMap.put('6', '6');
        hashMap.put('7', '7');
        hashMap.put('8', '8');
        hashMap.put('9', '9');
        hashMap.put('0', '0');
    }

    public int myAtoi(String s) {
        init();
        if ("".equals(s)) {
            return 0;
        }
        String value = s;

        // 从前开始遍历
        int start = 0;
        char c = 0;
        while (start < value.length()) {
            if ((c = value.charAt(start)) == ' ') {
                start++;
            } else {
                break;
            }
        }

        // 不能有效转换，返回 0
//        if (!hashMap.containsKey(c)) {
//            return 0;
//        }
        if(!(hashMap.containsKey(c) || hashMapFlag.containsKey(c))){
            return 0;
        }
        if(!(hashMap.containsKey(value.charAt(start+1)))){
            return 0;
        }

        int end = start+1;
        while (end < value.length()) {
            if (hashMap.containsKey(value.charAt(end))) {
                end++;
            } else {
                break;
            }
        }

//        System.out.println(value.substring(start, end));
        return stringToNum(value.substring(start, end));
    }

    public int stringToNum(String value) {
        int flag = 1;   // 表示是正数
        int skip = 0;   // 是否有正负号
        if (value.charAt(0) == '-') {
            flag = -1;
            skip = 1;
            if (value.length() == 1) {
                return 0;
            }
        } else if (value.charAt(0) == '+') {
            skip = 1;
            if (value.length() == 1) {
                return 0;
            }
        }
        int ans = 0;

        for (int i = skip; i < value.length(); i++) {
            if (ans * 10 / 10 != ans) {
                if (flag == 1) {
                    return Integer.MAX_VALUE;
                } else {
                    return Integer.MIN_VALUE;
                }
            }
            ans = ans * 10 + Integer.parseInt(value.charAt(i) + "");
        }
//        System.out.println(flag * ans);
        return flag * ans;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
//        s.myAtoi("     -42");
//       ;
//        s.myAtoi("  -41934 54545 with words");
//        s.myAtoi("  -0 54545 with words");
//        ;
//        ;
        System.out.println( s.myAtoi("4193 with words"));
        System.out.println(s.myAtoi("  -"));
        System.out.println(s.myAtoi("  +1"));
        System.out.println(s.myAtoi("     -42"));
        System.out.println(s.myAtoi("+-12"));
    }
}
