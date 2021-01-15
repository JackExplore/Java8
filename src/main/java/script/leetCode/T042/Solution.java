package script.leetCode.T042;

/**
 * ����ˮ����
 */
public class Solution {

    /**
     * ˼·��
     * 1������ÿ��Ԫ��������������ֵ��
     * 2��ȡԭ����������Сǽ�ߣ���ȥ��ǰǽ��ֵ����Ϊ�����ϵ�ˮ������
     * 3��ÿһ����ͣ�
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        if(height.length < 2){
            return 0;
        }

        int[] leftH = new int[height.length];
        int[] rightH = new int[height.length];

        // ������
        leftH[0] = 0;
        for (int i = 1; i < height.length; i++) {
            leftH[i] = leftH[i-1] > height[i-1] ? leftH[i-1] : height[i-1];
        }
//        printArr(leftH);

        // �ұ����
        rightH[height.length-1] = 0;
        for (int i = height.length-2; i >= 0; i--) {
            rightH[i] = rightH[i+1] > height[i+1] ? rightH[i+1] : height[i+1];
        }
//        printArr(rightH);

        int sum = 0;
        for (int i = 0; i < height.length; i++) {
            int minLeftRight = leftH[i] > rightH[i] ? rightH[i] : leftH[i];
            if(minLeftRight - height[i] > 0){
                sum += (minLeftRight - height[i]);
            }
        }

        return sum;
    }


    public static void main(String[] args) {
        Solution s = new Solution();

//        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        int[] height = {4,2,0,3,2,5};
        int sum = s.trap(height);
        System.out.println(sum);

    }

    public static void printArrays(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
