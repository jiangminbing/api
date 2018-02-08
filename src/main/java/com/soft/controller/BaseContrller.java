package com.soft.controller;

import com.alibaba.fastjson.JSON;
import com.soft.parent.basic.enums.PoolEnum;
import com.soft.parent.basic.redis.RedisConnection;
import com.soft.parent.basic.redis.RedisUtil;
import com.soft.parent.manager.po.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

/**
 * @Author jiangmb
 * @Time 2018/1/25.
 */
public abstract class BaseContrller {
    protected static Logger logger ;

    public BaseContrller(String name){
        logger= LoggerFactory.getLogger(name);
    }

    public void printParam(String param){
        logger.info("请求参数:{}",param);
    }
    @Autowired
    private RedisConnection redisConnection;
    protected boolean checkToken(Integer userId,String token)throws Exception{
        JedisPool jedisPool = redisConnection.getPool(PoolEnum.USER_TOKEN.getPool());
        String  userJson = RedisUtil.getValue(jedisPool,PoolEnum.USER_TOKEN.getKey()+token);
        if(StringUtils.isEmpty(userJson)){
            logger.info("token失效！");
            return false;
        }
        User user = JSON.parseObject(userJson,User.class);
        if(!user.getUserId().equals(userId)){
            logger.info("token与用户不匹配！");
            return false;
        }
        return true;
    }
}
