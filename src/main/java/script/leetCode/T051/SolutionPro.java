package script.leetCode.T051;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 皇后问题 - 提交版
 *
 * 注意问题步骤的分割，条件的判断，细节的分解
 */
public class SolutionPro {
    public List<List<String>> solveNQueens(int n) {

        List<List<String>> solutions = new ArrayList<List<String>>();

        List<int[]> foots = new LinkedList<>();

        research(solutions, n, 0, foots);

        return solutions;
    }

    private void research(List<List<String>> solutions, int n, int row, List<int[]> foots) {
        if (row == n) {
            List<String> solve = new ArrayList<>();
            for (int i = 0; i < foots.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    if(j == foots.get(i)[1]){
                        stringBuilder.append('Q');
                    }else {
                        stringBuilder.append('.');
                    }
                }
                solve.add(stringBuilder.toString());
//                System.out.println(stringBuilder.toString());
            }
//            System.out.println();
            solutions.add(solve);
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
        SolutionPro s = new SolutionPro();
        s.solveNQueens(4);
    }

}
