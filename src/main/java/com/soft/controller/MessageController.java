package com.soft.controller;

import com.alibaba.fastjson.JSON;
import com.soft.parent.basic.result.DetailResult;
import com.soft.parent.basic.result.ResCode;
import com.soft.parent.manager.po.Message;
import com.soft.service.ManagerService;
import com.soft.service.MessageService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author jiangmb
 * @Time 2018/2/8.
 */
@Controller
@RequestMapping("/front/message/message")
public class MessageController extends  BaseContrller{
    public MessageController() {
        super("MessageController");
    }
    @Autowired
    private ManagerService managerService;

    @Autowired
    private MessageService messageService;

    @ResponseBody
    @RequestMapping(value = "/getMessage",method = {RequestMethod.GET,RequestMethod.POST})
    public DetailResult<Message> getMessage(@ApiParam(required = true,value = "消息Id")@RequestParam(required = true) Integer messageId,
                                            @ApiParam(required = false,value = "用户Id")@RequestParam(required = false) Integer userId,
                                            @ApiParam(required = false,value = "token")@RequestParam(required = false) String token){
        try{
            if(userId!=null){
                if(!checkToken(userId,token))return new DetailResult(ResCode.TOKEN_INCORRECT);
            }
            return managerService.getMessageById(messageId);
        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }
    }
    /**
     *
     * @param messageId
     *            消息id
     * @param userMessageId
     *            消息记录id
     * @return 获取消息详情 并将消息记录置为已读
     */
    @ResponseBody
    @RequestMapping(value = "/getMessageAndRead",method = {RequestMethod.GET,RequestMethod.POST})
    public DetailResult<Message> getMessageAndRead(@ApiParam(required = true,value = "消息Id")@RequestParam(required = true) Integer messageId,
                                                   @ApiParam(required = true,value = "用户消息Id")@RequestParam(required = true) Integer userMessageId,
                                                   @ApiParam(required = true,value = "用户Id")@RequestParam(required = true) Integer userId,
                                                   @ApiParam(required = true,value = "token")@RequestParam(required = true) String token){
        try{

            if(!checkToken(userId,token))return new DetailResult(ResCode.TOKEN_INCORRECT);
            return messageService.getMessageAndRead(messageId,userMessageId);
        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }
    }
    /**
     *
     * @return 获取最新5条消息
     */
    @ResponseBody
    @RequestMapping(value = "/getMessageLevel",method = {RequestMethod.GET,RequestMethod.POST})
    public DetailResult<List<Message>> getMessageLevel(
            @ApiParam(required = true,value = "用户Id")@RequestParam(required = true) Integer userId,
            @ApiParam(required = true,value = "token")@RequestParam(required = true) String token){
        try{
            if(!checkToken(userId,token))return new DetailResult(ResCode.TOKEN_INCORRECT);
            return managerService.getMessageLevel(userId);
        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }
    }
//     不知道做什么用的
//    @ResponseBody
//	@RequestMapping("/getMessageByPage")
//	public JsonResponse<PageResult<Message>> getMessageByPage(PageResult<Message> page, Message Message,
//			HttpServletRequest request) {
//		JsonResponse<PageResult<Message>> result = new JsonResponse<PageResult<Message>>(SystemCode.FAILURE);
//		User user = SessionUtil.getUser(request);
//		Message.setUserId(user.getUserId());
//		messageService.queryByPageFront(page, Message);
//		if (page.getTotal() != 0) {
//			result.setRes(SystemCode.SUCCESS);
//			result.setObj(page);
//		}
//		return result;
//	}
    /************************* 消息记录 ********************************************/
    /**
     * 获取 当前用户未读消息数
     *
     * @param userId
     *            前端用户id
     */
    @ResponseBody
    @RequestMapping(value = "/getNoReadCount",method = {RequestMethod.GET,RequestMethod.POST})
    public DetailResult<Long> getNoReadCount(
            @ApiParam(required = true,value = "用户Id")@RequestParam(required = true) Integer userId,
            @ApiParam(required = true,value = "token")@RequestParam(required = true) String token){
        try{
            if(!checkToken(userId,token))return new DetailResult(ResCode.TOKEN_INCORRECT);
            return managerService.getNoReadCount(userId);
        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }
    }
    /**
     * 获取 当前用户消息数
     *
     * @param userId
     *            前端用户id
     */
    @ResponseBody
    @RequestMapping(value = "/getMessageCount",method = {RequestMethod.GET,RequestMethod.POST})
    public DetailResult<Long> getMessageCount(
            @ApiParam(required = true,value = "用户Id")@RequestParam(required = true) Integer userId,
            @ApiParam(required = true,value = "token")@RequestParam(required = true) String token){
        try{
            if(!checkToken(userId,token))return new DetailResult(ResCode.TOKEN_INCORRECT);
            return managerService.getMessageCount(userId);
        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }
    }

}
