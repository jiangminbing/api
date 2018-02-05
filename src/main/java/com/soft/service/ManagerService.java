package com.soft.service;

import com.soft.parent.basic.req.AdSearchDto;
import com.soft.parent.basic.req.GoodsCategoryDto;
import com.soft.parent.basic.req.GoodsPriceSearchDto;
import com.soft.parent.basic.req.GoodsSearchDto;
import com.soft.parent.basic.res.*;
import com.soft.parent.basic.result.DetailResult;
import com.soft.parent.basic.result.Page;
import com.soft.parent.basic.result.PageResult;
import com.soft.parent.basic.result.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author jiangmb
 * @Time 2018/1/26.
 */
@FeignClient(name= "manager")
public interface ManagerService {
    @RequestMapping(value = "/manager/getUserByMobile")
    @ResponseBody
    DetailResult<UserDto> getUserByMobile(@RequestParam String moible) throws Exception;

    @RequestMapping(value = "/manager/getUserPrivilegeByUser")
    @ResponseBody
    DetailResult<UserPrivilegeDto> getUserPrivilegeByUser(@RequestParam Integer userId) throws Exception;

    @RequestMapping(value = "/manager/getGoodsByGoodsCategory")
    @ResponseBody
    DetailResult<List<GoodsDto>> getGoodsByGoodsCategory(@RequestBody  GoodsCategoryDto dto) throws Exception;

    @RequestMapping(value = "/getPageGoodsByGoodsCategory",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    PageResult<GoodsDto> getPageGoodsByGoodsCategory(@RequestParam Page page, @RequestBody GoodsCategoryDto dto);

    @RequestMapping(value = "/getGoodsByPage",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    PageResult<GoodsDto> getGoodsByPage(@RequestBody GoodsSearchDto dto) throws Exception;

    @RequestMapping(value = "/findAllNormalGoodsPriceByGoodsId",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<List<GoodsPriceDto>> findAllNormalGoodsPriceByGoodsId(@RequestParam Integer goodsId) throws Exception;

    @RequestMapping(value = "/manager/getAdByPage")
    @ResponseBody
    PageResult<AdDto> getAdByPage(@RequestBody AdSearchDto dto) throws Exception;

    @RequestMapping(value = "/manager/queryShoppingCartByUserIdAndPriceId",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<ShoppingCartDto> queryShoppingCartByUserIdAndPriceId(@RequestParam Integer userId,@RequestParam Integer priceId) throws Exception;

    @RequestMapping(value = "/queryStoreGoodsByUserIdAndGoodsId")
    @ResponseBody
    DetailResult<List<StoreGoodsDto>> queryStoreGoodsByUserIdAndGoodsId(@RequestParam Integer userId,@RequestParam Integer goodsId) throws Exception;

    @RequestMapping(value = "/getGoodById",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<GoodsDto> getGoodById(@RequestParam Integer id)throws Exception;

    @RequestMapping(value = "/getPageByMyStoreGoods",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    PageResult<GoodsDto> getPageByMyStoreGoods(@RequestBody Page page,@RequestParam Integer userId)throws Exception;

    @RequestMapping(value = "/searchGoodsPrice",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<List<GoodsPriceDto>> searchGoodsPrice(@RequestBody GoodsPriceSearchDto searchDto) throws Exception;

    @RequestMapping(value = "/getListLogistics",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<List<LogisticsDto>> getListLogistics()throws Exception;




}
