package com.cj.system.utils.wxpay;


import java.security.MessageDigest;
import java.util.Random;
/**
 *
 * @author Wayne.Lee
 * @date 2017/09/20
 */
public class SignUtil {

    public static final String SIGN_TYPE_MD5 = "MD5";

    public static final String SIGN_TYPE_SHA1 = "SHA-1";

    private static String[] keys =new String[]{"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};


    public static String signSHA1(String data)throws Exception{
        return sign(data,SIGN_TYPE_SHA1);
    }

    public static String signMD5(String data)throws Exception{
        return sign(data,SIGN_TYPE_MD5);
    }



    /**
     * 返回指定长度的随机字符串
     * @param length
     * @return
     */
    public static String randomStr(int length){
        //随机字符串的随机字符库
        String KeyString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt(random.nextInt(len)));
        }
        return sb.toString();
    }

    public static String sign(String data,String signName)throws Exception{
        //指定sha1算法  
        MessageDigest digest = MessageDigest.getInstance(signName);
        digest.update(data.getBytes());
        //获取字节数组  
        byte messageDigest[] = digest.digest();
        // Create Hex String  
        StringBuilder hexString = new StringBuilder();
        // 字节数组转换为 十六进制 数  
        for (int i = 0,len = messageDigest.length; i < len; i++) {
            byte b = messageDigest[i];
            String s = keys[b&0xF];
            String p = keys[(b>>4)&0xF];
            hexString.append(p).append(s);
        }
        return hexString.toString();
    }

    public static void main(String[] args)throws Exception {
        System.out.println(SignUtil.sign("123456","SHA-1"));

        System.out.println(SignUtil.sign("123456","MD5"));
        System.out.println(SignUtil.sign("123456","SHA-256"));
        System.out.println(SignUtil.sign("123456","SHA-512"));


    }
}