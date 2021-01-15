package script.leetCode.T051;

import java.util.*;

/**
 * 皇后问题 - 手写实现版
 * 未返回指定结果
 */
public class Solution {
    public List<List<String>> solveNQueens(int n) {

        List<List<String>> solutions = new ArrayList<List<String>>();

        List<int[]> foots = new LinkedList<>();

        research(solutions, n, 0, foots);

        return null;
    }

    private void research(List<List<String>> solutions, int n, int row, List<int[]> foots) {
        if (row == n) {
            System.out.println(foots.toString());
            for(int[] arr : foots){
                System.out.print(arr[0] + " " + arr[1]);
                System.out.println();
            }
            return;
        }
        for (int k = 0; k < n; k++) {
            if (check(row, k, foots)) {
                int[] temp = new int[]{row, k};
                foots.add(temp);
                research(solutions, n, row + 1, foots);
                foots.remove(temp);
            }

        }
    }

    private boolean check(int row, int col, List<int[]> foot) {
        for (int[] arr : foot) {
            if (arr[1] == col) {
                return false;
            }
            if ((row - col) == (arr[0] - arr[1])) {
                return false;
            }
            if ((row + col) == (arr[0] + arr[1])) {
                return false;
            }

        }
        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        s.solveNQueens(4);
    }

}
