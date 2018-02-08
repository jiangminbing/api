package com.soft.service;

import com.alibaba.fastjson.JSON;
import com.soft.parent.basic.enums.PoolEnum;
import com.soft.parent.basic.redis.RedisConnection;
import com.soft.parent.basic.redis.RedisUtil;
import com.soft.parent.basic.result.DetailResult;
import com.soft.parent.basic.result.ResCode;
import com.soft.parent.basic.util.UUIDUtil;
import com.soft.parent.manager.po.User;
import com.soft.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;


/**
 * @Author jiangmb
 * @Time 2018/2/1.
 */
@Service
public class UserService {
    @Autowired
    private RedisConnection redisConnection;
    @Autowired
    private ManagerService managerService;
    public DetailResult<UserVo> login(String mobile, String psw) throws Exception{
        DetailResult<User> res = managerService.getUserByMobile(mobile);
        if(res.getStatus()!=ResCode.SUCCESS.getCode()) throw new Exception();
        User user = res.getData();
        /*用户不存在*/
        if(user==null){
            return new DetailResult(ResCode.USER_NOTEXISTS);
        }
        /*密码不匹配*/
        if(!psw.equals(user.getPsw())){
            return new DetailResult(ResCode.LOGIN_ERROR);
        }
        /*将用户信息保存到redis，返回token信息*/
        String token = saveUserRedis(user);
        UserVo userVo = new UserVo();
        userVo.setToken(token);
        DetailResult<UserVo> result = new DetailResult<>(ResCode.SUCCESS);
        result.setData(userVo);
        return result;
    }
    /**
     * 保存用户信息到缓存中 30分钟内有效
     * @param user
     * @throws Exception
     */
    protected String saveUserRedis(User user) throws Exception{
        String token = UUIDUtil.getUUID();
        JedisPool jedisPool = redisConnection.getPool(PoolEnum.USER_TOKEN.getPool());
        RedisUtil.saveStringByEx(jedisPool,PoolEnum.USER_TOKEN.getKey()+token, JSON.toJSONString(user),30);
        return token;
    }
    /**
     * 从缓存信息中获取用户信息
     * @param token
     * @return
     */
    public User getUserByToken(String token) throws Exception{
        JedisPool jedisPool = redisConnection.getPool(PoolEnum.USER_TOKEN.getPool());
        String  userJson = RedisUtil.getValue(jedisPool,PoolEnum.USER_TOKEN.getKey()+token);
        if(StringUtils.isNotEmpty(userJson)){
            User user = JSON.parseObject(userJson,User.class);
            return  user;
        }
        return  null;
    }

    /**
     * 将用户从缓存中移除
     * @param token
     * @throws Exception
     */
    public void removeUser(String token) throws Exception{
        JedisPool jedisPool = redisConnection.getPool(PoolEnum.USER_TOKEN.getPool());
        RedisUtil.delKey(jedisPool,PoolEnum.USER_TOKEN.getKey()+token);
    }



}
