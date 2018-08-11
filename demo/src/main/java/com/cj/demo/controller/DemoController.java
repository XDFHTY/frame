package com.cj.demo.controller;

import com.cj.core.aop.Log;
import com.cj.demo.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;


@RestController
@RequestMapping("/api/v1/demo")
@Api(tags = "测试Controller")
public class DemoController {

    @Resource
    private DemoService demoService;
    @GetMapping("/test")
    @ApiOperation(value = "测试事务")
    @ApiImplicitParam(name = "name",value = "数据库名称",required = true,defaultValue = "shentu")
    @Log(name = "Demo ==> 测试多数据源事务")
    public String testDemo(String name){
        demoService.testTra(name);

        return "测试完成";
    }

    /**
     * 将XML转换成HTML
     * @throws Exception
     */
    public static void translate() throws Exception{
        //创建XML的文件输入流
        FileInputStream fis=new FileInputStream("D:/lic/test/document.xml");
        Source source=new StreamSource(fis);

        //创建XSL文件的输入流
        FileInputStream fis1=new FileInputStream("F:/123.xsl");
        Source template=new StreamSource(fis1);

        PrintStream stm=new PrintStream(new File("F:/123.html"));
        //讲转换后的结果输出到 stm 中即 F:\123.html
        Result result=new StreamResult(stm);
        //根据XSL文件创建准个转换对象
        Transformer transformer=TransformerFactory.newInstance().newTransformer(template);
        //处理xml进行交换
        transformer.transform(source, result);
        //关闭文件流
        fis1.close();
        fis.close();
    }

    public static void main(String[] args){
        try {
            translate();
        } catch (Exception e) {
            System.out.println("XML转换成HTML失败："+e.getMessage());
        }
    }

}
