package script.leetCode.Offer040;

/**
 * 最小的 K 个数
 *
 */
public class Solution {

    public int[] getLeastNumbers(int[] arr, int k) {
        return null;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] arr = {3,2,1};

        int[] res = s.getLeastNumbers(arr, 2);
        printArrays(arr);
        printArrays(res);
    }

    public static void printArrays(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
