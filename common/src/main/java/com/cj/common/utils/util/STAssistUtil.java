package com.cj.common.utils.util;

import lombok.extern.slf4j.Slf4j;

import static com.cj.common.utils.util.MathExtend.divide;
import static com.cj.common.utils.util.MathExtend.multiply;
import static java.lang.Math.PI;

@Slf4j
public class STAssistUtil {


    /**
     * 比较两个浮点数最大值，返回最大值
     * @param a
     * @param b
     * @return
     */
    public static double getMax(double a,double b){

        if(a>=b){
            return a;

        }else {
            return b;
        }
    }

    /**
     * 字符串替换，替换� 等字符
     */
    public static String repJson(String json){
        json = json.replaceAll("��","%%533");
        json = json.replaceAll("�","%%533");

        return json;
    }

    /**
     * 字符串替换
     * 将[A-Z]、[、]、(、)、+、;、/ 进行替换，方便进行分割
     * @param str
     * @return
     */
    public static String repString(String str){

//        System.out.println("==========str==========="+str);

        String reg = "[A-Z]";

        str = str.replaceAll(reg,"");
        str = str.replaceAll("[\\[]","");
        str = str.replaceAll("[\\]]","");
        str = str.replaceAll("[\\(]","");
        str = str.replaceAll("[\\)]","");
        str = str.replaceAll("[\\+]","%%001");
        str = str.replaceAll("[\\;]","%%005");
        str = str.replaceAll("[\\/]","%%009");
        str = str.replaceAll(" ","%%000");

//        System.out.println("==========str==========="+str);
        return str;

    }

    /**
     * 字符串替换，将QT未能识别出的字符替换
     * 【QT中的未识别符号，java中的\uE532】替换为%%132
     * 返回替换后的字符串
     * 三级钢筋：%%132；二级钢筋：%%131；一级钢筋：%%130。
     *\U+0084【三级】，\U+0083【二级钢筋】，\U+0082【一级钢筋】
     */
    public static String rep(String str){
//        log.info("str1==>>{}",str);
        str = str.replace("\uE532","%%132");
        str = str.replace("\u0084","%%132");
        str = str.replace("\u0082","%%132");
        str = str.replace("\\U+0084","%%132");
        str = str.replace("\\U+0083","%%131");
        str = str.replace("\\U+0082","%%130");
//        log.info("str5==>>{}",str);

        return str;
    }

    /**
     * 检查数据是否包含%
     * 无%则返回无法判断
     * @return
     */
    public static String inspectString(String str){
        if(str.indexOf("%") == -1){
            return "========>>此数据无法判断";
        }else {
            return "";
        }

    }


    /**
     * 返回说明数字格式化
     * @param d
     * @return
     */
    public static String doDecimal(double d){
        return String.format("%.5f",d);
    }

    /**
     * 公式：N1 * d1 * d1 * PI/4
     * 计算支座配筋面积
     */
    public static double formula1(double n,double d){
        return multiply(multiply(multiply(n,d),d),divide(PI,4));
    }

    /**
     * 公式：ret1/wide/(height - 60)
     * 计算支座配筋率
     */
    public static double formula2(double ret,double wide,double height){
        return divide(divide(ret,wide),height-60);
    }

    /**
     * 公式：x = (ret1 * rebar_ten_param - ret7 * rebar_res_param）/[wide * (height - 60) * con_res_param
     * 计算受压区高度
     */
    public static double formula3(double reta,double rebar_tensile_param,double retb,double rebar_resist_param,double wide,double height,double concrete_resist_param){

        return divide((multiply(reta,rebar_tensile_param)- multiply(retb,rebar_resist_param)),multiply(multiply(wide,height - 60),concrete_resist_param));
    }

    /**
     * 公式：？？*con_ten_param/rebar_tensile_param/100
     * 计算支座最小配筋率
     */
    public static double formula4(double d,double concrete_tensile_param,double rebar_tensile_param){
        return divide(divide(multiply(d,concrete_tensile_param),rebar_tensile_param),100);
    }

}
