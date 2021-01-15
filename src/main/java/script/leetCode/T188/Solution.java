package script.leetCode.T188;

import java.util.*;

public class Solution {

    public static int maxProfit(int k, int[] prices) {

        if(prices == null || prices.length == 0 || k == 0){
            return 0;
        }
        // 1 ��������Ԫ�أ�����ÿ��ļ۸񸡶�����
        int[] profits = new int[prices.length];
        profits[0] = 0;
        System.out.print(profits[0] + " ");
        for (int i = 1; i < prices.length; i++) {
            profits[i] = prices[i] - prices[i - 1];
            System.out.print(profits[i] + " ");
        }
        System.out.println();
        // 2 ȡ��������������ֵ�ĺ�
        List<Integer> profitPositive = new ArrayList<>();
        profitPositive.add(0);
        boolean isLink = false;
        for (int i = 0; i < profits.length; i++) {
            if (profits[i] >= 0 && isLink) {    // ��������
                isLink = true;
                profitPositive.set(profitPositive.size() - 1, profitPositive.get(profitPositive.size() - 1) + profits[i]);
            } else if (profits[i] >= 0) {       // ��������
                profitPositive.add(profits[i]);
                isLink = true;
            } else {
                isLink = false;                 // ����
            }
        }
        // 2 ��ȡ����ֵ��������ȡ��ǰ k ��
        Object[] objects = profitPositive.toArray();
        System.out.println(Arrays.toString(objects));

        // 3 ǰ k ��֮�ͣ������������
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
