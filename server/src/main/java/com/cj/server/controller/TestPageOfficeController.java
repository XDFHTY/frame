package com.cj.server.controller;

import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordreader.WordDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.lang.annotation.Documented;
import java.util.Map;

@Controller
@RequestMapping
public class TestPageOfficeController {

    @Value("${posyspath}")
    private String poSysPath;

    @Value("${popassword}")
    private String poPassWord;

    @RequestMapping("/hello")
    public String test() {
        System.out.println("hello run");
        return "Hello";
    }

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public ModelAndView showIndex(){
        ModelAndView mv = new ModelAndView("Index");
        return mv;
    }

    @RequestMapping(value="/word", method=RequestMethod.GET)
    public ModelAndView showWord(HttpServletRequest request, Map<String,Object> map){

        PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
        poCtrl.setServerPage("/poserver.zz");//设置服务页面

        //向word指定位置写入数据
        com.zhuozhengsoft.pageoffice.wordwriter.WordDocument doc = new com.zhuozhengsoft.pageoffice.wordwriter.WordDocument();
        doc.openDataRegion("total").setValue("测试插入标题");
        doc.openDataRegion("number").setValue("测试插入编号");
        poCtrl.setWriter(doc);


        //添加自定义按钮
        poCtrl.addCustomToolButton("保存","Save",1);//添加自定义保存按钮
        poCtrl.addCustomToolButton("盖章","AddSeal",2);//添加自定义盖章按钮
        poCtrl.setSaveFilePage("/save");//设置处理文件保存的请求方法
        //打开word
        poCtrl.webOpen("file:///d:/lic/履历本样本_GJB2489_602.docx", OpenModeType.docNormalEdit,"张三");
        map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));


        ModelAndView mv = new ModelAndView("Word");
        return mv;
    }

    @GetMapping("/read")
    public ModelAndView readWord(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map){
        PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
        poCtrl.setServerPage("/poserver.zz");//设置服务页面



        //添加自定义按钮
        poCtrl.addCustomToolButton("提取数据","Readerlll",0);//添加自定义保存按钮
        poCtrl.addCustomToolButton("盖章","AddSeal",2);//添加自定义盖章按钮
        poCtrl.setSaveFilePage("/reader");//设置处理文件的请求方法
        //打开word
        poCtrl.webOpen("file:///d:/lic/file/履历本样本_GJB2489_602.docx", OpenModeType.docNormalEdit,"张三");
        map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));



        ModelAndView mv = new ModelAndView("Word");
        return mv;
    }

    @RequestMapping("/save")
    public void saveFile(HttpServletRequest request, HttpServletResponse response){
        FileSaver fs = new FileSaver(request, response);
        fs.saveToFile("d:/lic/file/" + fs.getFileName());
        fs.close();
    }


    @PostMapping("/reader")
    public void readFile(HttpServletRequest request, HttpServletResponse response){
        WordDocument doc = new WordDocument(request,response);
        String name = doc.openDataRegion("total").getValue();
        String num = doc.openDataRegion("number").getValue();
        System.out.println(name);
        System.out.println(num);

    }


    /**
     * 添加PageOffice的服务器端授权程序Servlet（必须）
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        com.zhuozhengsoft.pageoffice.poserver.Server poserver = new com.zhuozhengsoft.pageoffice.poserver.Server();
        poserver.setSysPath(poSysPath);//设置PageOffice注册成功后,license.lic文件存放的目录
        ServletRegistrationBean srb = new ServletRegistrationBean(poserver);
        srb.addUrlMappings("/poserver.zz");
        srb.addUrlMappings("/posetup.exe");
        srb.addUrlMappings("/pageoffice.js");
        srb.addUrlMappings("/jquery.min.js");
        srb.addUrlMappings("/pobstyle.css");
        srb.addUrlMappings("/sealsetup.exe");
        return srb;//
    }

    /**
     * 添加印章管理程序Servlet（可选）
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean2() {
        com.zhuozhengsoft.pageoffice.poserver.AdminSeal adminSeal = new com.zhuozhengsoft.pageoffice.poserver.AdminSeal();
        adminSeal.setAdminPassword(poPassWord);//设置印章管理员admin的登录密码

        adminSeal.setSysPath(poSysPath);//设置印章数据库文件poseal.db存放的目录
        ServletRegistrationBean srb = new ServletRegistrationBean(adminSeal);
        srb.addUrlMappings("/adminseal.zz");
        srb.addUrlMappings("/sealimage.zz");
        srb.addUrlMappings("/loginseal.zz");
        return srb;//
    }





}
