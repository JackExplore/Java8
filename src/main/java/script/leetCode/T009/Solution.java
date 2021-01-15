package script.leetCode.T009;

/**
 * »ØÎÄÊı
 */
public class Solution {

    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        return isDo(String.valueOf(x));
    }

    public boolean isDo(String value) {
        if (value.length() == 1) {
            return true;
        }
        if(value.length() == 2){
            return value.charAt(0) == value.charAt(1);
        }
        return (value.charAt(0)==value.charAt(value.length()-1)) && isDo(value.substring(1, value.length()-1));
    }

    public static void main(String[] args) {
        Solution so = new Solution();

        System.out.println(so.isPalindrome(121));
        System.out.println(so.isPalindrome(-121));
        System.out.println(so.isPalindrome(10));
//        String value = "121";
//        System.out.println(value.substring(1));
//        System.out.println(value.substring(1, 2));
//        System.out.println(value.charAt(2));
    }

}
