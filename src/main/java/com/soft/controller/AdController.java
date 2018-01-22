package com.soft.controller;

import com.soft.parent.basic.result.Result;
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
 * @Time 2018/1/22.
 */
@Controller
@RequestMapping("/front/ad/ad")
@CrossOrigin(origins = "*",methods={RequestMethod.POST,RequestMethod.GET})
@Api(value = "广告", description = "广告", position = 10)
public class AdController extends BaseController {
    @RequestMapping(value = "/getAdByPage",produces = "application/json; charset=utf-8")
    @ApiOperation(value = "分页获取广告信息")
    public Result getAdByPage(@ApiParam(required = true, value = "名称") @RequestParam(required = true) String name){

//        return "say:hello,"+name;
    }

}
