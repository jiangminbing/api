package com.soft.controller;

import com.soft.parent.basic.req.AdSearchDto;
import com.soft.parent.basic.result.PageResult;
import com.soft.parent.basic.result.ResCode;
import com.soft.parent.basic.result.Result;
import com.soft.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author jiangmb
 * @Time 2018/1/22.
 */
@Controller
@RequestMapping("/front/ad/ad")
@Api(value = "广告", description = "广告", position = 10)
public class AdController extends BaseContrller{

    @Autowired
    private ManagerService managerService;

    public AdController() {
        super("AdController");
    }

    @RequestMapping(value = "/getAdByPage",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "分页获取广告信息")
    public Result getAdByPage(@ApiParam(required = true, value = "名称") @RequestParam(required = true) String name){
        AdSearchDto dto = new AdSearchDto();
//        PageResult<AdDto> result = managerService.getAdByPage(dto);
        return new Result(ResCode.SYS_ERR);
    }

}
