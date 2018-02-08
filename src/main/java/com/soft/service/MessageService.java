package com.soft.service;


import com.soft.parent.basic.result.DetailResult;
import com.soft.parent.basic.result.ResCode;
import com.soft.parent.basic.result.Result;
import com.soft.parent.manager.po.Message;
import com.soft.parent.manager.po.UserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author jiangmb
 * @Time 2018/2/8.
 */
@Service
public class MessageService {
    private  static Logger logger = LoggerFactory.getLogger(MessageService.class);
    @Autowired
    private ManagerService managerService;

    public DetailResult<Message> getMessage(Integer messageId)throws Exception{
        return managerService.getMessageById(messageId);
    }

    public DetailResult<Message> getMessageAndRead(Integer messageId, Integer userMessageId) throws Exception{
        Message message = managerService.getMessageById(messageId).getData();
        if(message==null||message.getDelState()!=2){
            return new DetailResult<>(ResCode.NO_DATA);
        }
        UserMessage userMessage = managerService.getUserMessageById(userMessageId).getData();
        if(userMessage==null){
            logger.info("没用对应的用户消息 userMessageId=>{}",userMessageId);
            return  new DetailResult<>(ResCode.FAILED);
        }
        if(userMessage.getState()!=1){
            userMessage.setState((byte)1);
            userMessage.setUpdateTime(new Date());
            Result res = managerService.updateUserMessage(userMessage);
            if(res.getStatus()!=200){
                return new DetailResult<>(ResCode.FAILED);
            }
        }
        DetailResult<Message> result = new DetailResult<>(ResCode.SUCCESS);
        result.setData(message);
        return result;
    }
}
