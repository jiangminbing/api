package com.soft.service;

import com.soft.parent.basic.req.AdSearchDto;
import com.soft.parent.basic.res.AdDto;
import com.soft.parent.basic.result.PageResult;
import com.soft.parent.basic.result.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author jiangmb
 * @Time 2018/1/26.
 */
@FeignClient(name= "manager")
public interface AdFontService {

    @RequestMapping(value = "/manager/getAdByPage")
    @ResponseBody
    public PageResult<AdDto> getAdByPage(@RequestBody AdSearchDto dto);
}
