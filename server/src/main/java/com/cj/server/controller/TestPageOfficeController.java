package com.cj.server.controller;

import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OfficeVendorType;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

//        poCtrl.addCustomMenuItem("隐藏痕迹(&H)", "OnCustomMenuClick()", true);
//        poCtrl.addCustomMenuItem("-", "", false);

//        poCtrl.setCustomRibbon("2007");
//        poCtrl.setOfficeVendor(OfficeVendorType.MSOffice);
//        poCtrl.setVisible(true);

        WordDocument doc = new WordDocument();
        doc.openDataRegion("total").setValue("测试插入标题");
        doc.openDataRegion("number").setValue("测试插入编号");
        poCtrl.setWriter(doc);

        poCtrl.addCustomToolButton("保存","Save",1);//添加自定义保存按钮
        poCtrl.addCustomToolButton("盖章","AddSeal",2);//添加自定义盖章按钮
        poCtrl.setSaveFilePage("/save");//设置处理文件保存的请求方法
        //打开word
        poCtrl.webOpen("file:///d:/lic/履历本样本_GJB2489_602.docx", OpenModeType.docNormalEdit,"张三");
        map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));

        ModelAndView mv = new ModelAndView("Word");
        return mv;
    }
//
//    /**
//     * 向word输出数据
//     */
//    @RequestMapping(value="/word", method=RequestMethod.GET)
//    public ModelAndView setData(HttpServletRequest request){
//
//        PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
//        poCtrl1.setServerPage("/poserver.zz");//设置服务页面
//
//        WordDocument doc = new WordDocument();
//        doc.openDataRegion("total").setValue("测试插入标题");
//        doc.openDataRegion("number").setValue("测试插入编号");
//        poCtrl1.setWriter(doc);
//
//        poCtrl1.addCustomToolButton("保存","Save",1);//添加自定义保存按钮
//        poCtrl1.addCustomToolButton("盖章","AddSeal",2);//添加自定义盖章按钮
//        poCtrl1.setSaveDataPage("/save");
//
////        poCtrl1.setJsFunction_OnWordDataRegionClick("OnWordDataRegionClick()"); //JS函数响应时间
//        poCtrl1.webOpen("file:///d:/lic/履历本样本_GJB2489_602.docx", OpenModeType.docSubmitForm, "张三");
//        poCtrl1.setTagId("PageOfficeCtrl1"); // 此行必须
//
//        ModelAndView mv = new ModelAndView("Word");
//        return mv;
//    }

    @RequestMapping("/save")
    public void saveFile(HttpServletRequest request, HttpServletResponse response){
        FileSaver fs = new FileSaver(request, response);
        fs.saveToFile("d:/lic/file/" + fs.getFileName());
        fs.close();
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
