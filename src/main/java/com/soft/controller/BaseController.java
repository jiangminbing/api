package com.soft.controller;

import com.alibaba.fastjson.JSON;
import com.soft.parent.basic.redis.RedisConnection;
import com.soft.parent.basic.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author jiangmb
 * @Time 2018/1/19.
 */
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private RedisConnection redisConnection;

    protected boolean checkToken(String token,String userId){
        JedisPool jedisPool = redisConnection.getPool(PoolEnum.APP_TOKEN.getDb());
        String  customerJson = RedisUtil.getValue(jedisPool,PoolEnum.APP_TOKEN.getKey()+token);
        if(customerJson==null){
            logger.info("token失效！");
            return false;
        }
        Customer customer = JSON.parseObject(customerJson,Customer.class);
        if(customer==null){
            logger.info("token与用户不匹配！");
            return false;
        }
        if(!customer.getUserid().equals(userId)){
            logger.info("token与用户不匹配！");
            return false;
        }
        return  true;
    }
}
