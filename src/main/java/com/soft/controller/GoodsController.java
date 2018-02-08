package com.soft.controller;

import com.alibaba.fastjson.JSON;
import com.soft.parent.basic.req.GoodsCategoryDto;
import com.soft.parent.basic.req.GoodsSearchDto;
import com.soft.parent.basic.result.DetailResult;
import com.soft.parent.basic.result.Page;
import com.soft.parent.basic.result.PageResult;
import com.soft.parent.basic.result.ResCode;
import com.soft.parent.manager.po.GoodsPrice;
import com.soft.parent.manager.po.User;
import com.soft.service.GoodsService;
import com.soft.vo.GoodsVo;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author jiangmb
 * @Time 2018/2/2.
 */
@Controller
@RequestMapping("/front/goods/goods")
public class GoodsController extends BaseContrller {
    public GoodsController() {
        super("GoodsController");
    }
    @Autowired
    private GoodsService goodsService;

    /**
     * 查询商品
     * @param
     * @param userId
     * @param token
     * @return
     */
    @RequestMapping(value = "/getPageFrontByGoodsCategory",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DetailResult<List<GoodsVo>> getPageFrontByGoodsCategory(@ApiParam(required = true, value = "所属类别ID")@RequestParam(required = true)Integer categoryId,
                                                                   @ApiParam(required = true,value = "推荐 1是 2否")@RequestParam(required = true)Integer recommend,
                                                                   @ApiParam(required = false,value = "用户Id")@RequestParam(required = false) Integer userId,
                                                                   @ApiParam(required = false,value = "token")@RequestParam(required = false) String token){
        try{
            User user = null;
            if(userId!=null){
                if(!checkToken(userId,token))return new DetailResult(ResCode.TOKEN_INCORRECT);
                user = new User();
                user.setUserId(userId);
            }
            GoodsCategoryDto dto = new GoodsCategoryDto();
            dto.setCategoryId(categoryId);
            dto.setRecommend(recommend);


            return goodsService.getPageFrontByGoodsCategory(dto,user);

        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }

    }
    /**
     * 依据商品分类id获取所有商品，供前台调用，传的是VO对象，包含商品所有信息及附加信息
     */
    @ResponseBody
    @RequestMapping(value = "/getPageFrontVOByGoodsCategory",method = {RequestMethod.GET,RequestMethod.POST})
    public PageResult<GoodsVo>  getPageFrontVOByGoodsCategory(@ApiParam(required = true, value = "所属类别ID")@RequestParam(required = true)Integer categoryId,
                                                              @ApiParam(required = true, value = "页码") @RequestParam(required = true) Integer  pageNo,
                                                              @ApiParam(required = true, value = "每页显示") @RequestParam(required = true) Integer pageSize,
                                                              @ApiParam(required = false,value = "用户Id")@RequestParam(required = false) Integer userId,
                                                              @ApiParam(required = false,value = "token")@RequestParam(required = false) String token
                                                              ){
        try{
            User user = null;
            if(userId!=null){
                if(!checkToken(userId,token))return new PageResult(ResCode.TOKEN_INCORRECT);
                user = new User();
                user.setUserId(userId);
            }
            GoodsCategoryDto dto = new GoodsCategoryDto();
            dto.setCategoryId(categoryId);


            Page page = new Page();
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
            return goodsService.getPageFrontByGoodsCategory(dto,user,page);

        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new PageResult(ResCode.SYS_ERR);
        }

    }
    /**
     * 分页获取推荐的上架商品，依据更新时间倒序取商品,不含删除状态，供前端调用，传的是VO对象，包含商品所有信息及附加信息
     */
    @ResponseBody
    @RequestMapping(value = "/getPageFrontRecommendAndIsMarketableGoods",method = {RequestMethod.GET,RequestMethod.POST})
    public PageResult<GoodsVo> getPageFrontRecommendAndIsMarketableGoods(  @ApiParam(required = true, value = "页码") @RequestParam(required = true) Integer pageNo,
                                                                           @ApiParam(required = true, value = "每页显示") @RequestParam(required = true) Integer pageSize,
                                                                           @ApiParam(required = false,value = "用户Id")@RequestParam(required = false) Integer userId,
                                                                           @ApiParam(required = false,value = "token")@RequestParam(required = false) String token){
        try{
            User user = null;
            if(userId!=null){
                if(!checkToken(userId,token))return new PageResult(ResCode.TOKEN_INCORRECT);
                user = new User();
                user.setUserId(userId);
            }

            GoodsSearchDto searchDto = new GoodsSearchDto();
            searchDto.setDelState((byte)1);
            searchDto.setIsMarketable((byte)1);
            searchDto.setRecommend((byte)1);
            searchDto.setPageNo(pageNo);
            searchDto.setPageSize(pageSize);
            searchDto.setOrderBy("update_time");
            searchDto.setReverse(false);
            return goodsService.searchGoods(user,searchDto);

        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new PageResult(ResCode.SYS_ERR);
        }

    }
    /**
     * 依据商品id获取商品详情，供前台调用
     * 商品的delstate保存收藏状态，1为收藏，0为未收藏
     */
    @ResponseBody
    @RequestMapping(value = "/getPageFrontByGoodsId",method = {RequestMethod.GET,RequestMethod.POST})
    public DetailResult<List<GoodsVo>> getPageFrontByGoodsId(@ApiParam(required = true, value = "商品ID")@RequestParam(required = true)Integer goodsId,
                                                             @ApiParam(required = false,value = "用户Id")@RequestParam(required = false) Integer userId,
                                                             @ApiParam(required = false,value = "token")@RequestParam(required = false) String token){
        try{
            User user = null;
            if(userId!=null){
                if(!checkToken(userId,token))return new DetailResult(ResCode.TOKEN_INCORRECT);
                user = new User();
                user.setUserId(userId);
            }
            return goodsService.getPageFrontByGoodsId(user,goodsId);
        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }

    }
    /**
     * 依据商品名称模糊查询商品，供前台调用，传的是VO对象，包含商品所有信息及附加信息
     */
    @ResponseBody
    @RequestMapping(value = "/getPageFrontVOByGoodsName",method = {RequestMethod.GET,RequestMethod.POST})
    public PageResult<GoodsVo> getPageFrontVOByGoodsName(  @ApiParam(required = true, value = "商品名称") @RequestParam(required = true) String goodsName,
                                                           @ApiParam(required = true, value = "页码") @RequestParam(required = true) Integer pageNo,
                                                           @ApiParam(required = true, value = "每页显示") @RequestParam(required = true) Integer pageSize,
                                                           @ApiParam(required = false,value = "用户Id")@RequestParam(required = false) Integer userId,
                                                           @ApiParam(required = false,value = "token")@RequestParam(required = false) String token){
        try{
            User user = null;
            if(userId!=null){
                if(!checkToken(userId,token))return new PageResult(ResCode.TOKEN_INCORRECT);
                user = new User();
                user.setUserId(userId);
            }

            GoodsSearchDto searchDto = new GoodsSearchDto();
            searchDto.setDelState((byte)1);
            searchDto.setGoodsName(goodsName);
            searchDto.setIsMarketable((byte)1);
            searchDto.setPageNo(pageNo);
            searchDto.setPageSize(pageSize);
            searchDto.setOrderBy("update_time");
            searchDto.setReverse(false);
            return goodsService.searchGoods(user,searchDto);

        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new PageResult(ResCode.SYS_ERR);
        }
    }
    /**
     * 依据商品名称模糊查询商品，供前台调用，传的是VO对象，包含商品所有信息及附加信息
     */
    @ResponseBody
    @RequestMapping(value = "/getPageFrontByMyStoreGoods" ,method = {RequestMethod.GET,RequestMethod.POST})
    public PageResult<GoodsVo> getPageFrontByMyStoreGoods( @ApiParam(required = true, value = "页码") @RequestParam(required = true) Integer pageNo,
                                                          @ApiParam(required = true, value = "每页显示") @RequestParam(required = true) Integer pageSize,
                                                          @ApiParam(required = true,value = "用户Id")@RequestParam(required = true) Integer userId,
                                                          @ApiParam(required = true,value = "token")@RequestParam(required = true) String token){
        try{
            if(!checkToken(userId,token))return new PageResult(ResCode.TOKEN_INCORRECT);
            User user = new User();
            user.setUserId(userId);

            Page page =new Page(pageNo,pageSize);
            return goodsService.getPageFrontByMyStoreGoods(user,page);
        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new PageResult(ResCode.SYS_ERR);
        }

    }
    /**
     * 查找商品规格，已启用的，显示的实际价放在零售价字段里（依据用户不同价格不一样），购物车中的数量放在批发价字段里
     */
    @ResponseBody
    @RequestMapping(value = "/getGoodsPriceListByGoodsId" ,method = {RequestMethod.GET,RequestMethod.POST})
    public DetailResult<List<GoodsPrice>>  getGoodsPriceListByGoods(@ApiParam(required = true,value = "商品Id")@RequestParam(required = true) Integer goodId,
                                                                    @ApiParam(required = false,value = "用户Id")@RequestParam(required = false) Integer userId,
                                                                    @ApiParam(required = false,value = "token")@RequestParam(required = false) String token){
        try{
            User user = null;
            if(userId!=null){
                if(!checkToken(userId,token))return new DetailResult(ResCode.TOKEN_INCORRECT);
                user = new User();
                user.setUserId(userId);
            }

            return goodsService.getGoodsPriceByGoodsId(user,goodId);

        }catch (Exception e){
            logger.error("系统异常:{}", JSON.toJSONString(e));
            return new DetailResult(ResCode.SYS_ERR);
        }
    }



}
