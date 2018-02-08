package com.soft.service;

import com.soft.parent.basic.req.AdSearchDto;
import com.soft.parent.basic.req.GoodsCategoryDto;
import com.soft.parent.basic.req.GoodsPriceSearchDto;
import com.soft.parent.basic.req.GoodsSearchDto;
import com.soft.parent.basic.result.DetailResult;
import com.soft.parent.basic.result.Page;
import com.soft.parent.basic.result.PageResult;
import com.soft.parent.basic.result.Result;
import com.soft.parent.manager.po.*;
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
    DetailResult<User> getUserByMobile(@RequestParam String moible) throws Exception;

    @RequestMapping(value = "/manager/getUserPrivilegeByUser")
    @ResponseBody
    DetailResult<UserPrivilege> getUserPrivilegeByUser(@RequestParam Integer userId) throws Exception;

    @RequestMapping(value = "/manager/getGoodsByGoodsCategory")
    @ResponseBody
    DetailResult<List<Goods>> getGoodsByGoodsCategory(@RequestBody  GoodsCategoryDto dto) throws Exception;

    @RequestMapping(value = "/getPageGoodsByGoodsCategory",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    PageResult<Goods> getPageGoodsByGoodsCategory(@RequestParam Page page, @RequestBody GoodsCategoryDto dto);

    @RequestMapping(value = "/getGoodsByPage",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    PageResult<Goods> getGoodsByPage(@RequestBody GoodsSearchDto dto) throws Exception;

    @RequestMapping(value = "/findAllNormalGoodsPriceByGoodsId",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<List<GoodsPrice>> findAllNormalGoodsPriceByGoodsId(@RequestParam Integer goodsId) throws Exception;

    @RequestMapping(value = "/manager/getAdByPage")
    @ResponseBody
    PageResult<Ad> getAdByPage(@RequestBody AdSearchDto dto) throws Exception;

    @RequestMapping(value = "/manager/queryShoppingCartByUserIdAndPriceId",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<ShoppingCart> queryShoppingCartByUserIdAndPriceId(@RequestParam Integer userId,@RequestParam Integer priceId) throws Exception;

    @RequestMapping(value = "/queryStoreGoodsByUserIdAndGoodsId")
    @ResponseBody
    DetailResult<List<StoreGoods>> queryStoreGoodsByUserIdAndGoodsId(@RequestParam Integer userId,@RequestParam Integer goodsId) throws Exception;

    @RequestMapping(value = "/getGoodById",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<Goods> getGoodById(@RequestParam Integer id)throws Exception;

    @RequestMapping(value = "/getPageByMyStoreGoods",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    PageResult<Goods> getPageByMyStoreGoods(@RequestBody Page page,@RequestParam Integer userId)throws Exception;

    @RequestMapping(value = "/searchGoodsPrice",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<List<GoodsPrice>> searchGoodsPrice(@RequestBody GoodsPriceSearchDto searchDto) throws Exception;

    @RequestMapping(value = "/getListLogistics",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<List<Logistics>> getListLogistics()throws Exception;

    @RequestMapping(value = "getMessageById",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<Message> getMessageById(@RequestParam Integer messageId) throws Exception;

    @RequestMapping(value = "getUserMessageById",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<UserMessage> getUserMessageById(Integer userMessageId);

    @RequestMapping(value = "updateUserMessage",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    Result updateUserMessage(@RequestBody UserMessage userMessage) throws Exception;

    @RequestMapping(value = "updateUserMessage",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<List<Message>> getMessageLevel(Integer userId) throws Exception;

    @RequestMapping(value = "getNoReadCount",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    DetailResult<Long> getNoReadCount(@RequestParam Integer userId) throws Exception;

    @RequestMapping(value = "getMessageCount",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DetailResult<Long> getMessageCount(@RequestParam Integer userId) throws Exception;

}
