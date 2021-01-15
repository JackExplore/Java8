package script.leetCode.T200;

/**
 * 岛屿问题思路：
 * 1 每遇到岛屿 +1；
 * 2 依次广度优先遍历当前岛屿，并将值置为 0；
 * 3 检索岛屿前后左右，判断范围，并将周边置为 0；
 *
 */
public class Solution {

    public int numIslands(char[][] grid) {

        if (grid == null || grid.length < 0) {
            return 0;
        }

        int num = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    num++;
                    isLands(grid, i, j);
                } else {
                    continue;
                }
            }
        }

        return num;
    }

    public void isLands(char[][] grid, int i, int j) {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
            if (grid[i][j] == '0') {
                return;
            } else {
                grid[i][j] = '0';
                isLands(grid, i - 1, j);
                isLands(grid, i + 1, j);
                isLands(grid, i, j - 1);
                isLands(grid, i, j + 1);
            }
        } else {
            return;
        }
    }

    public static void main(String[] args) {

        Solution s = new Solution();

        String[][] grids = {{"1", "1", "0", "0", "0"},
                {"1", "1", "0", "0", "0"},
                {"0", "0", "1", "0", "0"},
                {"0", "0", "0", "1", "1"}};


        char[][] grid = new char[grids.length][grids[0].length];

        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[0].length; j++) {
                grid[i][j] = grids[i][j].charAt(0);
            }
        }


        System.out.println(s.numIslands(grid));

    }
}
