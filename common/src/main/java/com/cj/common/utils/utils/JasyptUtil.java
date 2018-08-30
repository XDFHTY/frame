package com.cj.common.utils.utils;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;


/**
 * @author Exrickx
 */
@Slf4j
public class JasyptUtil {

    /**
     * Jasypt生成加密结果
     * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value 待加密值
     * @return
     */
    public static String encyptPwd(String password,String value){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.encrypt(value);
        return result;
    }

    /**
     * 解密
     * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value 待解密密文
     * @return
     */
    public static String decyptPwd(String password,String value){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.decrypt(value);
        return result;
    }

    public static SimpleStringPBEConfig cryptor(String password){
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }

    public static void main(String[] args){

        //加密
        System.out.println(encyptPwd("jewe","jdbc:mysql://118.123.16.211:3306/jewe_pro?characterEncoding=UTF-8&useSSL=true&allowMultiQueries=true"));
        System.out.println(encyptPwd("jewe","root"));
        System.out.println(encyptPwd("jewe","rootpass"));
        //解密
        System.out.println(decyptPwd("witschool","D2iSQiHhY5Nv3HtABHgSlsW9gYa+DUq3uRjDHQMzzlMkV7Q5xRY1XXOyEO/Shf5icKgXk4rUEDO2ao1OjhilyqV9RmeEbPQMMND4pP69NCAYwCT/Zpry0Ez5wo2ccYlKyx8qT78mMlC6o5JIhs52Sg=="));
    }
}
