package com.cj.system.utils.wxpay;


import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 微信支付工具类，处理xml数据
 * @author Wayne.Lee
 * @date 2017/09/20
 *
 */
public class XMLUtil {

    /**
     * 根据参数xml转换为map对象，基于微信支付，不做深层处理
     *
     * @param xml
     * @return
     * @throws Exception
     */
    public static Map<String, String> parserXML(String xml) throws Exception {

        DocumentBuilder doc = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document d = doc.parse(new ByteArrayInputStream(xml.getBytes()));
        Element el = d.getDocumentElement();
        NodeList list = el.getChildNodes();
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0, len = list.getLength(); i < len; i++) {
            Node n = list.item(i);
            if (n.hasChildNodes()) {
                Node f = n.getFirstChild();
                short ft = f.getNodeType();
                if (ft == Node.CDATA_SECTION_NODE || ft == Node.TEXT_NODE) {
                    map.put(n.getNodeName(), f.getNodeValue());
                }
            }
        }


        return map;
    }


    /**
     * 将map转换为xml数据，偷懒处理，全部数据都用<![CDATA[]]>包装
     *
     * @param map
     * @return
     * @throws Exception
     */
    public static String getXML(Map<String, String> map) throws Exception {
        StringBuilder sb = new StringBuilder("<xml>");
        for (Map.Entry<String, String> en : map.entrySet()) {
            String key = en.getKey();
            sb.append("<" + key + "><![CDATA[");
            sb.append(en.getValue());
            sb.append("]]></" + key + ">");
        }
        return sb.append("</xml>").toString();
    }

}