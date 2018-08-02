package com.cj.system.utils.pay;


import com.cj.system.utils.alipay.config.AlipayConfig;
import com.cj.system.utils.alipay.util.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * 支付基本信息
 * @author zhangli
 * @date 2016年3月28日 下午2:35:24
 *
 *
 * 1.需要支付的信息  ：
 *         用户支付  支付相关业务 支付金额  回调业务
 * 2.第三方支付需要的信息：
 *         trade_no,subject,body,total_fee,notify_url
 *
 */
public class PayUtils {

    public static String sign(String content, String RSA_PRIVATE) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * 支付宝支付
     * @param trade_no      订单号
     * @param total_fee     金额
     * @param subject       标题
     * @param body          内容
     * @param notify_url    回调地址
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String AliPay(String trade_no, Double total_fee, String subject, String body, String notify_url) throws UnsupportedEncodingException{
        String orderInfo = "_input_charset=\"utf-8\"";
        orderInfo += "&body=" + "\"" + body + "\"";
        orderInfo += "&it_b_pay=\"30m\"";
        orderInfo += "&notify_url=" + "\"" + notify_url + "\"";
        orderInfo += "&out_trade_no=" + "\"" + trade_no + "\"";
        orderInfo += "&partner=" + "\"" + AlipayConfig.partner + "\"";
        orderInfo += "&payment_type=\"1\"";
        orderInfo += "&return_url=\"" + notify_url + "\"";
        orderInfo += "&seller_id=" + "\"" + AlipayConfig.seller_email + "\"";
        orderInfo += "&service=\"mobile.securitypay.pay\"";
        orderInfo += "&subject=" + "\"" + subject + "\"";
        String format = String.format("%.2f", total_fee);
        if(format.indexOf(",")>0)format = format.replace(",", ".");
        orderInfo += "&total_fee=" + "\"" + format+"\"";
        System.out.println(orderInfo);
        String sign = sign(orderInfo, AlipayConfig.private_key);
        sign = URLEncoder.encode(sign, "utf-8");
        return orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=\"RSA\"";
    }
}