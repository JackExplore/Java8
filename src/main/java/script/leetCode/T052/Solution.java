package script.leetCode.T052;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 皇后问题 - 返回数量
 */
public class Solution {
    int count = 0;
    public int totalNQueens(int n) {

//        List<List<String>> solutions = new ArrayList<List<String>>();

        List<int[]> foots = new LinkedList<>();

        research(/*solutions,*/ n, 0, foots);

        return count;
    }

    private void research(/*List<List<String>> solutions, */int n, int row, List<int[]> foots) {
        if (row == n) {
            count++;
            return;
        }
        for (int k = 0; k < n; k++) {
            if (check(row, k, foots)) {
                int[] temp = new int[]{row, k};
                foots.add(temp);
                research(/*solutions, */n, row + 1, foots);
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
        int n = s.totalNQueens(4);
        System.out.println(n);
    }

}
