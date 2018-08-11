package com.cj.demo.controller;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.render.RenderAPI;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PoiTlController {

    public static void main(String[] args) throws Exception {
        //构造数据
        Map<String, Object> datas = new HashMap<String, Object>(){{
            put("header_version", "ver 0.0.3");
            put("logo", new PictureRenderData(100, 120, "src/test/resources/logo.png"));
            put("title", new TextRenderData("9d55b8", "Deeply in love with the things you love,\\n just deepoove."));
            put("changeLog", new TableRenderData(new ArrayList<RenderData>(){{
                add(new TextRenderData("d0d0d0", ""));
                add(new TextRenderData("d0d0d0", "introduce"));
            }}, new ArrayList<Object>(){{
                add("1;add new # gramer");
                add("2;support insert table");
                add("3;support more style");
            }}, "no datas", 10600));
            put("website", "http://www.deepoove.com/poi-tl");
        }};

        //读取模板，进行渲染
        XWPFTemplate doc = XWPFTemplate
                .create("src/test/resources/PB.docx");
        RenderAPI.render(doc, datas);

        //输出渲染后的文件
        FileOutputStream out = new FileOutputStream("out.docx");
        doc.write(out);
        out.flush();
        out.close();
    }
}
