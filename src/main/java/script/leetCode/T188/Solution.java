package script.leetCode.T188;

import java.util.*;

public class Solution {

    public static int maxProfit(int k, int[] prices) {

        if(prices == null || prices.length == 0 || k == 0){
            return 0;
        }
        // 1 遍历数组元素，计算每天的价格浮动利润
        int[] profits = new int[prices.length];
        profits[0] = 0;
        System.out.print(profits[0] + " ");
        for (int i = 1; i < prices.length; i++) {
            profits[i] = prices[i] - prices[i - 1];
            System.out.print(profits[i] + " ");
        }
        System.out.println();
        // 2 取出连续的正数的值的和
        List<Integer> profitPositive = new ArrayList<>();
        profitPositive.add(0);
        boolean isLink = false;
        for (int i = 0; i < profits.length; i++) {
            if (profits[i] >= 0 && isLink) {    // 连续利润
                isLink = true;
                profitPositive.set(profitPositive.size() - 1, profitPositive.get(profitPositive.size() - 1) + profits[i]);
            } else if (profits[i] >= 0) {       // 新增利润
                profitPositive.add(profits[i]);
                isLink = true;
            } else {
                isLink = false;                 // 负数
            }
        }
        // 2 对取出的值进行排序，取出前 k 个
        Object[] objects = profitPositive.toArray();
        System.out.println(Arrays.toString(objects));

        // 3 前 k 个之和，即是最大利润
        int result = 0;
        Integer[] arrProfits = new Integer[objects.length];
        for (int i = 0; i < arrProfits.length; i++) {
            arrProfits[i] = (int)objects[i];
        }

        Arrays.sort(arrProfits);
        System.out.println(Arrays.toString(arrProfits));
        for (int i = arrProfits.length - 1; i > 0 && k > 0; i--, k--) {
            result += arrProfits[i];
        }

        return result;

    }

    public static void main(String[] args) {

        int k = 2;
        int[] prices = new int[]{1,2,4,2,5,7,2,4,9,0};
        System.out.println(maxProfit(k, prices));

    }
}
