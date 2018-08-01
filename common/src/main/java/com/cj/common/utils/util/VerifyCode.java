package com.cj.common.utils.util;

import java.util.Random;

public class VerifyCode {

    /**生成随机数字验证码
     * @param number 验证码位数
     * @return
     */
    public static String getRandomNumCode(int number){
        String codeNum = "";
        int [] numbers = {0,1,2,3,4,5,6,7,8,9};
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int next = random.nextInt(10000);//目的是产生足够随机的数，避免产生的数字重复率高的问题
//    			System.out.println(next);
            codeNum+=numbers[next%10];
        }
        System.out.println("--------");
        System.out.println(codeNum);

        return codeNum;
    }
    /**生成随机数字验证码
     * 默认4位
     * @return
     */
    public static String getRandomNumCode(){
        String codeNum = "";
        int [] numbers = {0,1,2,3,4,5,6,7,8,9};
        Random random = new Random();
        int number = 4;
        for (int i = 0; i < number; i++) {
            int next = random.nextInt(10000);//目的是产生足够随机的数，避免产生的数字重复率高的问题
//    			System.out.println(next);
            codeNum+=numbers[next%10];
        }
        System.out.println("--------");
        System.out.println(codeNum);

        return codeNum;
    }
    /**生产带字母验证码
     * @param number
     * @return
     */
    public static String getRandomCode(int number){
        String codeNum = "";
        int [] code = new int[3];
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int num = random.nextInt(10) + 48;
            int uppercase = random.nextInt(26) + 65;
            int lowercase = random.nextInt(26) + 97;
            code[0] = num;
            code[1] = uppercase;
            code[2] = lowercase;
            codeNum+=(char)code[random.nextInt(3)];
        }
        //System.out.println(codeNum);

        return codeNum;
    }
    /**生产带字母验证码
     * 默认4位验证码
     * @return
     */
    public static String getRandomCode(){
        String codeNum = "";
        int [] code = new int[3];
        Random random = new Random();
        int number=4;
        for (int i = 0; i < number; i++) {
            int num = random.nextInt(10) + 48;
            int uppercase = random.nextInt(26) + 65;
            int lowercase = random.nextInt(26) + 97;
            code[0] = num;
            code[1] = uppercase;
            code[2] = lowercase;
            codeNum+=(char)code[random.nextInt(3)];
        }
        //System.out.println(codeNum);

        return codeNum;
    }
    public static void main(String[] args) {
        System.out.println(getRandomNumCode(4));
    }
}
