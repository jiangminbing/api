package com.soft.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author jiangmb
 * @Time 2018/1/18.
 */
@Controller
@CrossOrigin(origins = "*",methods={RequestMethod.POST,RequestMethod.GET})
@Api(value = "测试", description = "测试", position = 10)
public class TestController {
    @RequestMapping(value = "/test",produces = "application/json; charset=utf-8")
    @ApiOperation(value = "测试")
    public String test(@ApiParam(required = true, value = "名称") @RequestParam(required = true) String name){
        return "say:hello,"+name;
    }

}
