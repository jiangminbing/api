package com.soft.controller;

import com.alibaba.fastjson.JSON;
import com.soft.parent.basic.res.UserDto;
import com.soft.parent.basic.result.DetailResult;
import com.soft.parent.basic.result.ResCode;
import com.soft.parent.basic.result.Result;
import com.soft.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author jiangmb
 * @Time 2018/2/1.
 */
@Controller
@RequestMapping("/front/user/user")
@Api(value = "用户信息", description = "用户信息", position = 10)
public class UserController extends BaseContrller{
    public UserController() {
        super("UserController");
    }
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/toLogin",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "用户登录")
    public DetailResult<UserDto> login(@ApiParam(required = true, value = "电话号码") @RequestParam(required = true) String mobile,
                                       @ApiParam(required = true,value = "密码")@RequestParam(required = true)String psw){
        try {
            printParam("toLogin==>mobile:"+mobile+",psw:"+psw);
            return userService.login(mobile,psw);
        }catch (Exception e){
            logger.error("系统异常:{}",JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/logout",method={RequestMethod.POST,RequestMethod.GET})
    public Result logout(@ApiParam(required = true, value = "用户ID") @RequestParam(required = true) Integer userId,
                         @ApiParam(required = true,value = "token")@RequestParam(required = true)String token) {
        try{
            /*token校验*/
            if(!checkToken(userId,token)) return new Result(ResCode.TOKEN_INCORRECT);
            /*移除*/
            userService.removeUser(token);
            return  new Result(ResCode.SUCCESS);
        }catch (Exception e){
            logger.error("系统异常:{}",JSON.toJSONString(e));
            return new Result(ResCode.SYS_ERR);
        }

    }

}
