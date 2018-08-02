package com.cj.system.utils.wxpay;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class UrlUtil {

    public static String urlGet(String url)throws Exception{
        GetMethod get = new GetMethod(url);
        HttpClient httpClient = new HttpClient();
        get.getParams().setContentCharset("utf-8");
        httpClient.executeMethod(get);
        return get.getResponseBodyAsString();
    }


    public static String urlPost(String url,String postBody)throws Exception{
        PostMethod post = new PostMethod(url);
        HttpClient httpClient = new HttpClient();
        post.getParams().setContentCharset("utf-8");
        post.setRequestBody(postBody);
        httpClient.executeMethod(post);
        return post.getResponseBodyAsString();
    }

}