package script.leetCode.T017;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电话号码
 *
 */
public class Solution {

    Map<Character, String> dic = new HashMap<Character, String>();
    List<String> result = new ArrayList<>();

    /**
     * Key-Function
     *
     * 带参数的递归，深度增加的同时修改了数据
     *
     * @param digits
     * @param index
     * @param s
     */
    public void findCombination(String digits, int index, String s) {
        if(index == digits.length()){
            result.add(s);
            return;
        }
        char c = digits.charAt(index);
        String letters = dic.get(c);
        for (int i = 0; i < letters.length(); i++) {
            findCombination(digits, index + 1, s + letters.charAt(i));
        }
        return;
    }

    public List<String> letterCombinations(String digits) {
        if ("".equals(digits)) {
            return result;
        }
        dic.put('1', "");
        dic.put('2', "abc");
        dic.put('3', "def");
        dic.put('4', "ghi");
        dic.put('5', "jkl");
        dic.put('6', "mno");
        dic.put('7', "pqrs");
        dic.put('8', "tuv");
        dic.put('9', "wxyz");

        findCombination(digits, 0, "");

        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        s.letterCombinations("2345");
        for (int i = 0; i < s.result.size(); i++) {
            System.out.println(s.result.get(i));
        }
    }
}
