package com.cj.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@Api(tags = "DemoController")
public class DemoController {

    @GetMapping("/testDemo")
    @ApiOperation(value = "册数",notes = "内容",tags = "DemoController")
    public String testDemo(){
        return "测试成功";
    }
}
