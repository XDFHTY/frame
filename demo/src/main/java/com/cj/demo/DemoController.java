package com.cj.demo;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@Api(tags = "DemoController")
public class DemoController {

    @GetMapping("/testDemo")
    public String testDemo(){
        return "测试成功";
    }
}
