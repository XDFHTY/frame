package com.cj.common.utils.http;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

public class APIHttpClient {

    // 接口地址
    private static String apiURL = "http://icm.uat.52zzb.com/cm/channelService";
    private Log logger = LogFactory.getLog(this.getClass());
    private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";
    private long startTime = 0L;
    private long endTime = 0L;
    private int status = 0;

    /**
     * 接口地址
     ** @param *url
     */
    public APIHttpClient() {}

    /**
     * 调用 API
     * @param
     * *parameters
     * @return
     */

    public String post(String taskUrl, HttpServletRequest request, String parameters, int flag) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(apiURL+taskUrl);
        System.out.println("请求地址===>>"+httpPost);
        String body = null;
        logger.info("parameters:" + parameters);
        if (httpPost != null & parameters != null && !"".equals(parameters.trim())) {
            try {
                httpPost.setHeader("Content-type","application/json");
                if(flag == 1){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("channelId",(String)request.getSession().getAttribute("channelId"));
                    jsonObject.put("channelSecret",(String)request.getSession().getAttribute("channelSecret"));
                    parameters = jsonObject.toString();
                }
                else {
                    httpPost.setHeader("channelId", (String)request.getSession().getAttribute("channelId"));
                    httpPost.setHeader("accessToken", (String)request.getSession().getAttribute("accessToken"));
                    httpPost.setHeader("nonceStr", (String)request.getSession().getAttribute("nonceStr"));
                    httpPost.setHeader("signStr", (String)request.getSession().getAttribute("signStr"));
                    for(Header h:httpPost.getAllHeaders()){
                        logger.info("header:" + h);
                    }
                }
                System.out.println("请求参数=========>"+parameters);
                httpPost.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
                startTime = System.currentTimeMillis();
                HttpResponse response = httpClient.execute(httpPost);
                endTime = System.currentTimeMillis();
                int statusCode = response.getStatusLine().getStatusCode();
                logger.info("statusCode:" + statusCode);
                logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("Method failed:" + response.getStatusLine());
                    status = 1;
                }
                body = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                status = 3;
            } finally {
                logger.info("调用接口状态：" + status);
            }
        }
        return body;
    }

    public int getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    /**
     * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
     *
     * @return the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }
}