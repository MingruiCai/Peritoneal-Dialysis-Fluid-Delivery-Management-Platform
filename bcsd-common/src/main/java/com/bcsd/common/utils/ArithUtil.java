package com.bcsd.common.utils;

import java.math.BigDecimal;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
 * 确的浮点数运算，包括加减乘除和四舍五入。
 * Created by liuliang on 2016/9/9.
 */
public class ArithUtil {

    public static final int DEF_DIV_SCALE = 4; //默认除法运算精度

    private ArithUtil(){} //这个类不能实例化

    /**
     * 提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static String add(String v1,String v2){
        BigDecimal b1 = new BigDecimal(StringUtils.isEmpty(v1)?"0":v1);
        BigDecimal b2 = new BigDecimal(StringUtils.isEmpty(v2)?"0":v2);
        return b1.add(b2).toString();
    }

    /**
     * 提供精确的加法运算
     * @param v
     * @return 多个参数的和
     */
    public static String adds(String ... v){
        if (v.length==0){
            return "0";
        }
        if (v.length==1){
            return v[0];
        }
        String v1 = v[0];
        for (int i = 1;i<v.length;i++){
            v1 = add(v1,v[i]);
        }
        return v1;
    }

    /**
     * 提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        return Double.parseDouble(add(String.valueOf(v1),String.valueOf(v2)));
    }

    /**
     * 提供精确的加法运算
     * @param v
     * @return 多个参数的和
     */
    public static double adds(double ... v){
        if (v.length==0){
            return 0d;
        }
        if (v.length==1){
            return v[0];
        }
        double v1 = v[0];
        for (int i = 1;i<v.length;i++){
            v1 = add(v1,v[i]);
        }
        return v1;
    }

    /**
     * 提供精确的减法运算
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static String sub(String v1,String v2){
        BigDecimal b1 = new BigDecimal(StringUtils.isEmpty(v1)?"0":v1);
        BigDecimal b2 = new BigDecimal(StringUtils.isEmpty(v2)?"0":v2);
        return b1.subtract(b2).toString();
    }

    /**
     * 提供精确的减法运算
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        return Double.parseDouble(sub(String.valueOf(v1),String.valueOf(v2)));
    }

    /**
     * 提供精确的减法运算
     * @param v
     * @return 多个参数的和
     */
    public static String sub(String ... v){
        if (v.length==0){
            return "0";
        }
        if (v.length==1){
            return v[0];
        }
        String v1 = v[0];
        for (int i = 1;i<v.length;i++){
            v1 = sub(v1,v[i]);
        }
        return v1;
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).toString();
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        return Double.parseDouble(sub(String.valueOf(v1),String.valueOf(v2)));
    }

    /**
     * 提供精确的乘法运算
     * @param v
     * @return 多个参数的积
     */
    public static String muls(String ... v){
        if (v.length==0){
            return "0";
        }
        if (v.length==1){
            return v[0];
        }
        String v1 = v[0];
        for (int i = 1;i<v.length;i++){
            v1 = mul(v1,v[i]);
        }
        return v1;
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后6位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static String div(String v1,String v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后6位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return Double.parseDouble(div(String.valueOf(v1),String.valueOf(v2)));
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static String div(String v1,String v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(StringUtils.isBlank(v1)?"0":v1);
        BigDecimal b2 = new BigDecimal(StringUtils.isBlank(v2)?"0":v2);
        if (b2.doubleValue()==0){
            return "0";
        }
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 提供精确的小数位处理。
     * @param v 需要处理的数字
     * @param scale 小数点后保留几位
     * @param roundType 格式化类型（）
     * @return 结果
     */
    public static String round(String v,int scale,int roundType){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,roundType).toString();
    }

    /**
     * 提供精确的小数位处理。
     * @param v 需要处理的数字
     * @param scale 小数点后保留几位
     * @param roundType 格式化类型（）
     * @return 结果
     */
    public static Float round(Float v,int scale,int roundType){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale,roundType).floatValue();
    }

    /**
     * 格式化保留两位小数
     * @param v
     * @return
     */
    public static String format(String v){
        return round(v,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 格式化保留两位小数
     * @param v
     * @return
     */
    public static Float format(Float v){
        return round(v,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 格式化
     * @param v
     * @param scale
     * @return
     */
    public static String format(String v,int scale){
        return round(v,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 格式化
     * @param v
     * @param scale
     * @return
     */
    public static Float format(Float v,int scale){
        return round(v,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 比较大小
     * @param v1
     * @param v2
     * @return
     */
    public static int compare(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }

    /**
     * 百分比
     * @param v1
     * @return
     */
    public static String rate(String v1){
        return format(mul(v1,"100"),1);
    }


}
