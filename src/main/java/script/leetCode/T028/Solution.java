package script.leetCode.T028;

public class Solution {

    public static int strStr(String haystack, String needle) {
        if ("".equals(haystack) && "".equals(needle)) {
            return 0;
        }
        if (haystack == "") {
            return -1;
        }
        if (needle == "") {
            return 0;
        }
        if (needle.length() > haystack.length()) {
            return -1;
        }

        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            int k = i;
            int j = 0;
            while (haystack.charAt(k++) == needle.charAt(j++)) {
                if (j == needle.length()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int strStrPro(String haystack, String needle) {
        if ("".equals(haystack) && "".equals(needle)) {
            return 0;
        }
        if (haystack == "") {
            return -1;
        }
        if (needle == "") {
            return 0;
        }
        if (needle.length() > haystack.length()) {
            return -1;
        }

        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            if (haystack.substring(i, needle.length() + i).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String haystack = "hello";
        String needle = "ll";
        System.out.println(strStr(haystack, needle));
    }
}
