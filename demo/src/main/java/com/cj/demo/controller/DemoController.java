package com.cj.demo.controller;

import com.cj.core.aop.Log;
import com.cj.core.config.datasource.TargetDataSource;
import com.cj.demo.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
