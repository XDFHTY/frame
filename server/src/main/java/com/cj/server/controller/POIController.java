package com.cj.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class POIController {



        /**
         * 读取word文件内容
         *
         * @param path
         * @return buffer
         */

        public String readWord(String path) {
            String buffer = "";
            try {
                if (path.endsWith(".doc")) {
                    InputStream is = new FileInputStream(new File(path));
                    WordExtractor ex = new WordExtractor(is);
                    buffer = ex.getText();
                    ex.close();
                } else if (path.endsWith("docx")) {
                    OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                    POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                    buffer = extractor.getText();
                    extractor.close();
                } else {
                    System.out.println("此文件不是word文件！");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return buffer;
        }



    public static void main(String[] args) {
        // TODO Auto-generated method stub
        POIController tp = new POIController();
        String content = tp.readWord("D:/lic/履历本样本_GJB2489_602.docx");
        System.out.println("content===="+content);
    }

}
