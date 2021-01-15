package test;


import java.math.BigDecimal;

public class DoubleCompare {
    public String compare(BigDecimal val1, BigDecimal val2) {
        java.lang.String result = "";
        if (val1.compareTo(val2) < 0) {
            result = "第二位数大！";
        }
        if (val1.compareTo(val2) == 0) {
            result = "两位数一样大！";
        }
        if (val1.compareTo(val2) > 0) {
            result = "第一位数大！";
        }
        return result;
    }

    public static void main(java.lang.String[] args) {
        double a = 0.01;
        double b = 0.001 * 10;
        BigDecimal data1 = new BigDecimal(a);
        BigDecimal data2 = new BigDecimal(b);
        System.out.print(new DoubleCompare().compare(data1, data2));
    }
}
