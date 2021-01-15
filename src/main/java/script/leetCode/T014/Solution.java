package script.leetCode.T014;

/**
 * ��ͬǰ׺�Ӵ�
 */
public class Solution {

    public String longestCommonPrefix(String[] strs) {
        // ǰ�������ж�
        if(strs == null || strs.length < 1){
            return "";
        }
        if(strs.length == 1){
            return strs[0];
        }
        // ����ִ������
        StringBuilder common = new StringBuilder();
        String comp = strs[0];

        for (int i = 0; i < comp.length(); i++) {
            for (int j = 1; j < strs.length; j++) {
                if(i > strs[j].length()-1 || comp.charAt(i) != strs[j].charAt(i)){  // ע���ڲ������ַ������ȵ��ж�
                    return common.toString();
                }
            }
            common.append(comp.charAt(i));
        }

        return common.toString();
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        String[] ss = {"ab","a"};
        System.out.println(s.longestCommonPrefix(ss));
    }
}
