package script.leetCode.T003;

import java.util.HashSet;

/**
 * ���ظ��ַ�����Ӵ�
 */
public class Solution {

    /**
     * ��������
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring1(String s) {
        if (s == null || s.length() < 1) {
            return 0;
        }
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {

            StringBuilder sb = new StringBuilder();
            for (int j = i; j < s.length(); j++) {
                // �жϵ�ǰ���Ӵ��Ƿ����Ҫ�ӽ�����Ԫ��
                String s1 = sb.toString();
                char c0 = s.charAt(j);
                boolean has = false;
                for (char c : s1.toCharArray()) {
                    if (c0 == c) {
                        has = true;
                        break;
                    }
                }
                if (has) {
                    break;
                }
                sb.append(s.charAt(j));
                if (sb.length() > maxLen) {
                    maxLen = sb.length();
                }
            }
        }
        return maxLen;
    }

    /**
     * �������ڸ���
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() < 1) {
            return 0;
        }

        int maxLen = 0;
        HashSet<Character> bin = new HashSet<>();
        int pointerleft = 0;
        for (int i = 0; i < s.length(); ) {
            char c = s.charAt(i);
            if (!bin.contains(c)) {                       // �Ҳ���ǰ��
                bin.add(c);
                maxLen = Math.max(bin.size(), maxLen);
                i++;
            } else {                                      // �����ǰ��
                bin.remove(s.charAt(pointerleft++));
            }
        }
        return maxLen;
    }


    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(s.lengthOfLongestSubstring("bbbbb"));
        System.out.println(s.lengthOfLongestSubstring("pwwkew"));
        System.out.println(s.lengthOfLongestSubstring("aab"));
    }
}
