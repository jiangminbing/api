package com.soft.controller;

import com.alibaba.fastjson.JSON;
import com.soft.parent.basic.req.OrderSearchDto;
import com.soft.parent.basic.result.DetailResult;
import com.soft.parent.basic.result.PageResult;
import com.soft.parent.basic.result.ResCode;
import com.soft.parent.basic.result.Result;
import com.soft.parent.manager.model.OrderDto;
import com.soft.parent.manager.po.User;
import com.soft.service.ManagerService;
import com.soft.service.UserService;
import com.soft.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ManagerService managerService;
    @RequestMapping(value = "/toLogin",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "用户登录")
    public DetailResult<UserVo> login(@ApiParam(required = true, value = "电话号码") @RequestParam(required = true) String mobile,
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

    /**
     * 用户提交订单
     * @param order
     * @param userId
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addOrder",method={RequestMethod.POST,RequestMethod.GET})
    public DetailResult<String> addOrder(@ApiParam(required = true, value = "订单信息") @RequestBody(required = true)OrderDto order,
                           @ApiParam(required = true, value = "用户ID") @RequestParam(required = true) Integer userId,
                           @ApiParam(required = true,value = "token")@RequestParam(required = true)String token) {
        try {
            if(!checkToken(userId,token)) return new DetailResult(ResCode.TOKEN_INCORRECT);
            order.setUser(userService.getUserByToken(token));
            return managerService.addOrder(order);
        } catch (Exception e) {
            logger.error("系统异常:{}",JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }

    }

    /**
     * 查询用户订单
     * @param payState  支付状态 1.未付款 2 已付款
     * @param paymentId 支付方式id
     * @param logisticsState 配送状态 1 待待配 2  已配送 3 已确认收货
     * @param pageNo
     * @param pageSize
     * @param userId
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderByPage",method={RequestMethod.POST,RequestMethod.GET})
    public PageResult<OrderDto> getOrderByPage(@ApiParam(required = true, value = "支付状态") @RequestParam(required = true)Integer payState,
                                               @ApiParam(required = true, value = "支付方式") @RequestParam(required = true)Integer paymentId,
                                               @ApiParam(required = true, value = "配送状态") @RequestParam(required = true)Integer logisticsState,
                                               @ApiParam(required = true, value = "页码") @RequestParam(required = true) Integer pageNo,
                                               @ApiParam(required = true, value = "每页显示") @RequestParam(required = true) Integer pageSize,
                                               @ApiParam(required = true, value = "用户ID") @RequestParam(required = true) Integer userId,
                                               @ApiParam(required = true,value = "token")@RequestParam(required = true)String token) {
        try{
            if(!checkToken(userId,token)) return new PageResult(ResCode.TOKEN_INCORRECT);
            OrderSearchDto searchDto = new OrderSearchDto();
            searchDto.setUserId(userId);
            searchDto.setPayState(payState.byteValue());
            searchDto.setPaymentId(paymentId);
            searchDto.setLogisticsState(logisticsState.byteValue());
            searchDto.setPageNo(pageNo);
            searchDto.setPageSize(pageSize);
            searchDto.setOrderBy("order_id");
            searchDto.setReverse(false);
            return managerService.searchOrder(searchDto);
        }catch (Exception e){
            logger.error("系统异常:{}",JSON.toJSONString(e));
            return new PageResult(ResCode.SYS_ERR);
        }
    }
    /**
     * 获取累计未结算的订单
     */
    @ResponseBody
    @RequestMapping(value = "/getLJOrderByPage",method={RequestMethod.POST,RequestMethod.GET})
    public PageResult<OrderDto> getLJOrderByPage(
                                                 @ApiParam(required = true, value = "页码") @RequestParam(required = true) Integer pageNo,
                                                 @ApiParam(required = true, value = "每页显示") @RequestParam(required = true) Integer pageSize,
                                                 @ApiParam(required = true, value = "用户ID") @RequestParam(required = true) Integer userId,
                                                 @ApiParam(required = true,value = "token")@RequestParam(required = true)String token) {
        try{
            if(!checkToken(userId,token)) return new PageResult(ResCode.TOKEN_INCORRECT);
            OrderSearchDto searchDto = new OrderSearchDto();
            searchDto.setUserId(userId);
            searchDto.setPayState((byte) 1);
            searchDto.setPaymentId(3);
            searchDto.setPageNo(pageNo);
            searchDto.setPageSize(pageSize);
            searchDto.setOrderBy("order_id");
            searchDto.setReverse(false);
            return managerService.searchOrder(searchDto);
        }catch (Exception e){
            logger.error("系统异常:{}",JSON.toJSONString(e));
            return new PageResult(ResCode.SYS_ERR);
        }
    }

    /**
     * 查询订单详情
     * @param orderNumber
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderByOrderNumber",method={RequestMethod.POST,RequestMethod.GET})
    public DetailResult<OrderDto> getOrderByOrderNumber(@ApiParam(required = true, value = "订单编号") @RequestParam(required = true) String orderNumber){
        try{
            return managerService.getOrderByOrderNum(orderNumber);
        }catch (Exception e){
            logger.error("系统异常:{}",JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }
    }




}
