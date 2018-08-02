package com.cj.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Api(tags = "可及的痕迹sad")
public class AdminTestController {


    @ApiOperation("asjd ")
    public void tst(@ApiParam(name = "s",value = "测试",required = true) String s){

    }
}
