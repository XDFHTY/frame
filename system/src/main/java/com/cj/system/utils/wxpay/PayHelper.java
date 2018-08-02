package com.cj.system.utils.wxpay;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;


public class PayHelper {

    private static Logger log = Logger.getLogger(PayHelper.class);
    /**
     * 统一下单请求地址
     */
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * tradeType 下单类型：JSAPI--公众号支付
     */
    public static final String TRADE_TYPE_JSAPI = "JSAPI";

    /**
     * tradeType 下单类型：APP--app支付
     */
    public static final String TRADE_TYPE_APP = "APP";

    /**
     * tradeType 下单类型NATIVE--原生扫码支付
     */
    public static final String TRADE_TYPE_NATIVE = "NATIVE";


    private String appId = null;
    private String mchId = null;
    private String mchKey = null;


    public PayHelper(String appId, String mchId,String mchKey) {
        this.appId = appId;
        this.mchId = mchId;
        this.mchKey = mchKey;
    }



    /**
     * 公众号统一下单
     * @param openId
     * @param fee
     * @param tradeNo
     * @param notifyUrl
     * @param ip
     * @param body
     * @param more 更多参数参见微信文档
     * @return
     * @throws Exception
     */
    public String unifiedH5(String openId,int fee,String tradeNo,String notifyUrl,String ip,String body,Map<String,String> more)throws Exception{
        Map<String,String> map = new HashMap<String, String>();

        map.put("openid",openId);
        map.put("total_fee",String.valueOf(fee));
        map.put("out_trade_no",tradeNo);
        map.put("notify_url",notifyUrl);
        map.put("spbill_create_ip",ip);
        map.put("body",body);

        if(null!=more){
            map.putAll(more);
        }


        return unified(TRADE_TYPE_JSAPI,map);
    }


    /**
     * 根据预支付id 生成包含所有必须参数的map对象
     * @param prepayId
     * @return
     * @throws Exception
     */
    public Map<String,String> getClientPrepayMap(String prepayId)throws Exception{
        Map<String,String> map = new HashMap<String,String>();
        map.put("appId",appId);
        map.put("timeStamp",String.valueOf(new Date().getTime()/1000));
        map.put("nonceStr",SignUtil.randomStr(16));
        map.put("package","prepay_id="+prepayId);
        map.put("signType","MD5");
        String sign = unifiedSign(map,mchKey,SignUtil.SIGN_TYPE_MD5);
        map.put("paySign",sign);

        return map;
    }




    /**
     * 统一下单签名
     * @param map map只关心业务参数即可，随机字符串，sign签名后在方法内部加入map对象中
     * @param key
     * @return 签名字符串
     * @throws Exception
     */
    public String unifiedSign(Map<String,String> map,String key,String signType)throws Exception{

        TreeMap<String,String> treeMap = new TreeMap<String, String>(map);
        treeMap.remove("key");
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String> e:treeMap.entrySet()){
            String v = e.getValue();
            if(null==v||"null".equalsIgnoreCase(v)||"".equals(v)){
                continue;
            }
            sb.append("&").append(e.getKey()).append("=").append(e.getValue());
        }
        String data = sb.substring(1);
        if(null!=key){
            data += "&key="+key;
        }

        log.debug("统一下单（使用"+signType+"）签名字符串："+data);
        String sign = SignUtil.sign(data,signType).toUpperCase();

        return sign;
    }

    /**
     * 统一下单，返回预支付id
     * @param tradeType 下单类型：JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，
     * @param payParas
     * @return
     * @throws Exception
     */
    private String unified(String tradeType,Map<String,String> payParas)throws Exception{
        String prepayId = "";

        Map<String,String> map = new HashMap<String, String>(payParas);
        map.put("appid",appId);
        map.put("mch_id",mchId);
        map.put("trade_type",tradeType);
        map.put("nonce_str",SignUtil.randomStr(16));


        String sign = unifiedSign(map,mchKey,SignUtil.SIGN_TYPE_MD5);
        map.put("sign",sign);

        String postXML = XMLUtil.getXML(map);
        String str = UrlUtil.urlPost(UNIFIEDORDER_URL,postXML);
        log.debug(tradeType+"请求预支付返回结果："+str);
        Map<String,String> result =XMLUtil.parserXML(str);

        prepayId = result.get("prepay_id");
        if(null==prepayId){
            throw new Exception("获取预支付id失败");
        }
        return prepayId;
    }

}
