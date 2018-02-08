package com.soft.service;

import com.soft.parent.basic.req.GoodsCategoryDto;
import com.soft.parent.basic.req.GoodsPriceSearchDto;
import com.soft.parent.basic.req.GoodsSearchDto;
import com.soft.parent.basic.result.DetailResult;
import com.soft.parent.basic.result.Page;
import com.soft.parent.basic.result.PageResult;
import com.soft.parent.basic.result.ResCode;
import com.soft.parent.manager.po.*;
import com.soft.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author jiangmb
 * @Time 2018/2/2.
 */
@Service
public class GoodsService {
    @Autowired
    private ManagerService managerService;

    /**
     * 查询所有商品
     * @param dto
     * @param user
     * @return
     * @throws Exception
     */
    public DetailResult<List<GoodsVo>> getPageFrontByGoodsCategory(GoodsCategoryDto dto, User user) throws Exception{
        DetailResult<List<GoodsVo>> result = new DetailResult<>(ResCode.SUCCESS);
        List<Goods> list = managerService.getGoodsByGoodsCategory(dto).getData();
        if(list==null||list.isEmpty())return result;
        List<GoodsVo> goodsVOList = new ArrayList<>();
        for(Goods goods:list){
            GoodsVo temGoodsVo = new GoodsVo(goods);
            List<GoodsPrice> goodsPriceDtoList = managerService.findAllNormalGoodsPriceByGoodsId(goods.getGoodsId()).getData();
            goodsPriceDtoList = getCurrentUserGoodsprice(goodsPriceDtoList,user);
            if (goodsPriceDtoList == null || goodsPriceDtoList.size() == 0) {
                temGoodsVo.setVo_countGoodsPrice(0);
            }else {
                temGoodsVo.setVo_countGoodsPrice(goodsPriceDtoList.size());
                for (int j = 0; j < goodsPriceDtoList.size(); j++) {
                    if (temGoodsVo.getVo_retailPrice() == 0) {
                        temGoodsVo.setVo_retailPrice(goodsPriceDtoList
                                .get(j).getRetailPrice());
                    } else if (temGoodsVo.getVo_retailPrice() > goodsPriceDtoList
                            .get(j).getRetailPrice()) {
                        temGoodsVo.setVo_retailPrice(goodsPriceDtoList
                                .get(j).getRetailPrice());
                    }
                }
                temGoodsVo.setVo_shoppingCartNum(goodsPriceDtoList.get(0)
                        .getBuyPrice());
                temGoodsVo.setVo_priceId(goodsPriceDtoList.get(0)
                        .getPriceId());
                temGoodsVo.setVo_unitName(goodsPriceDtoList.get(0)
                        .getUnitName());
            }
            goodsVOList.add(temGoodsVo);
        }
        result.setData(goodsVOList);
        return  result;
    }

    /**
     * 分页查询
     * @param dto
     * @param user
     * @param page
     * @return
     * @throws Exception
     */
    public PageResult<GoodsVo> getPageFrontByGoodsCategory(GoodsCategoryDto dto, User user,Page page) throws Exception{
        PageResult<GoodsVo> result = new PageResult<>(ResCode.SUCCESS);
        PageResult<Goods> res = managerService.getPageGoodsByGoodsCategory(page,dto);
        List<Goods> list = res.getData();
        if(list==null||list.isEmpty())return result;
        List<GoodsVo> goodsVOList = new ArrayList<>();
        for(Goods goods:list){
            GoodsVo temGoodsVo = new GoodsVo(goods);
            List<GoodsPrice> goodsPriceDtoList = managerService.findAllNormalGoodsPriceByGoodsId(goods.getGoodsId()).getData();
            goodsPriceDtoList = getCurrentUserGoodsprice(goodsPriceDtoList,user);
            if (goodsPriceDtoList == null || goodsPriceDtoList.size() == 0) {
                temGoodsVo.setVo_countGoodsPrice(0);
            }else {
                temGoodsVo.setVo_countGoodsPrice(goodsPriceDtoList.size());
                for (int j = 0; j < goodsPriceDtoList.size(); j++) {
                    if (temGoodsVo.getVo_retailPrice() == 0) {
                        temGoodsVo.setVo_retailPrice(goodsPriceDtoList
                                .get(j).getRetailPrice());
                    } else if (temGoodsVo.getVo_retailPrice() > goodsPriceDtoList
                            .get(j).getRetailPrice()) {
                        temGoodsVo.setVo_retailPrice(goodsPriceDtoList
                                .get(j).getRetailPrice());
                    }
                }
                temGoodsVo.setVo_shoppingCartNum(goodsPriceDtoList.get(0)
                        .getBuyPrice());
                temGoodsVo.setVo_priceId(goodsPriceDtoList.get(0)
                        .getPriceId());
                temGoodsVo.setVo_unitName(goodsPriceDtoList.get(0)
                        .getUnitName());
            }
            goodsVOList.add(temGoodsVo);
        }
        result.setData(goodsVOList);
        result.setTotal(res.getTotal());
        return  result;
    }
    public PageResult<GoodsVo> searchGoods(User user, GoodsSearchDto searchDto)throws Exception{
        PageResult<GoodsVo> result = new PageResult<>(ResCode.SUCCESS);
        PageResult<Goods> res = managerService.getGoodsByPage(searchDto);
        List<Goods> list = res.getData();
        if(list==null||list.isEmpty())return result;
        List<GoodsVo> goodsVOList = new ArrayList<>();
        for(Goods goods:list){
            GoodsVo temGoodsVo = new GoodsVo(goods);
            List<GoodsPrice> goodsPriceDtoList = managerService.findAllNormalGoodsPriceByGoodsId(goods.getGoodsId()).getData();
            goodsPriceDtoList = getCurrentUserGoodsprice(goodsPriceDtoList,user);
            if (goodsPriceDtoList == null || goodsPriceDtoList.size() == 0) {
                temGoodsVo.setVo_countGoodsPrice(0);
            }else {
                temGoodsVo.setVo_countGoodsPrice(goodsPriceDtoList.size());
                for (int j = 0; j < goodsPriceDtoList.size(); j++) {
                    if (temGoodsVo.getVo_retailPrice() == 0) {
                        temGoodsVo.setVo_retailPrice(goodsPriceDtoList
                                .get(j).getRetailPrice());
                    } else if (temGoodsVo.getVo_retailPrice() > goodsPriceDtoList
                            .get(j).getRetailPrice()) {
                        temGoodsVo.setVo_retailPrice(goodsPriceDtoList
                                .get(j).getRetailPrice());
                    }
                }
                temGoodsVo.setVo_shoppingCartNum(goodsPriceDtoList.get(0)
                        .getBuyPrice());
                temGoodsVo.setVo_priceId(goodsPriceDtoList.get(0)
                        .getPriceId());
                temGoodsVo.setVo_unitName(goodsPriceDtoList.get(0)
                        .getUnitName());
            }
            goodsVOList.add(temGoodsVo);
        }
        result.setData(goodsVOList);
        result.setTotal(res.getTotal());
        return  result;

    }
    public DetailResult<List<GoodsVo>> getPageFrontByGoodsId(User user,Integer goodsId)throws Exception{
        DetailResult<List<GoodsVo>> result = new DetailResult<>();
        List<Goods> goodsList = new ArrayList<>();
        Goods tempgoods = managerService.getGoodById(goodsId).getData();
        if(tempgoods!=null){
            if(user==null){
                tempgoods.setDelState((byte)0);//未收藏
            }else {
                if(managerService.queryStoreGoodsByUserIdAndGoodsId(user.getUserId(),goodsId).getData()==null){
                    tempgoods.setDelState((byte)0);//未收藏
                }else {
                    tempgoods.setDelState((byte)1);//已收藏
                }
                goodsList.add(tempgoods);
                List<GoodsVo> goodsVOList = new ArrayList<GoodsVo>();
                for(Goods goods:goodsList){
                    GoodsVo temGoodsVo = new GoodsVo(goods);
                    List<GoodsPrice> goodsPriceDtoList = managerService.findAllNormalGoodsPriceByGoodsId(goods.getGoodsId()).getData();
                    goodsPriceDtoList = getCurrentUserGoodsprice(goodsPriceDtoList,user);
                    if (goodsPriceDtoList == null || goodsPriceDtoList.size() == 0) {
                        temGoodsVo.setVo_countGoodsPrice(0);
                    }else {
                        temGoodsVo.setVo_countGoodsPrice(goodsPriceDtoList.size());
                        for (int j = 0; j < goodsPriceDtoList.size(); j++) {
                            if (temGoodsVo.getVo_retailPrice() == 0) {
                                temGoodsVo.setVo_retailPrice(goodsPriceDtoList
                                        .get(j).getRetailPrice());
                            } else if (temGoodsVo.getVo_retailPrice() > goodsPriceDtoList
                                    .get(j).getRetailPrice()) {
                                temGoodsVo.setVo_retailPrice(goodsPriceDtoList
                                        .get(j).getRetailPrice());
                            }
                        }
                        temGoodsVo.setVo_shoppingCartNum(goodsPriceDtoList.get(0)
                                .getBuyPrice());
                        temGoodsVo.setVo_priceId(goodsPriceDtoList.get(0)
                                .getPriceId());
                        temGoodsVo.setVo_unitName(goodsPriceDtoList.get(0)
                                .getUnitName());
                    }
                    goodsVOList.add(temGoodsVo);
                }
                result.setData(goodsVOList);
                return  result;
            }
        }
        return  result;

    }
    public PageResult<GoodsVo> getPageFrontByMyStoreGoods(User user,Page page)throws Exception{
        PageResult<GoodsVo> result = new PageResult<>(ResCode.SUCCESS);
        PageResult<Goods> res = managerService.getPageByMyStoreGoods(page,user.getUserId());
        List<Goods> list = res.getData();
        if(list==null||list.isEmpty())return result;
        List<GoodsVo> goodsVOList = new ArrayList<>();
        for(Goods goods:list){
            GoodsVo temGoodsVo = new GoodsVo(goods);
            List<GoodsPrice> goodsPriceDtoList = managerService.findAllNormalGoodsPriceByGoodsId(goods.getGoodsId()).getData();
            goodsPriceDtoList = getCurrentUserGoodsprice(goodsPriceDtoList,user);
            if (goodsPriceDtoList == null || goodsPriceDtoList.size() == 0) {
                temGoodsVo.setVo_countGoodsPrice(0);
            }else {
                temGoodsVo.setVo_countGoodsPrice(goodsPriceDtoList.size());
                for (int j = 0; j < goodsPriceDtoList.size(); j++) {
                    if (temGoodsVo.getVo_retailPrice() == 0) {
                        temGoodsVo.setVo_retailPrice(goodsPriceDtoList
                                .get(j).getRetailPrice());
                    } else if (temGoodsVo.getVo_retailPrice() > goodsPriceDtoList
                            .get(j).getRetailPrice()) {
                        temGoodsVo.setVo_retailPrice(goodsPriceDtoList
                                .get(j).getRetailPrice());
                    }
                }
                temGoodsVo.setVo_shoppingCartNum(goodsPriceDtoList.get(0)
                        .getBuyPrice());
                temGoodsVo.setVo_priceId(goodsPriceDtoList.get(0)
                        .getPriceId());
                temGoodsVo.setVo_unitName(goodsPriceDtoList.get(0)
                        .getUnitName());
            }
            goodsVOList.add(temGoodsVo);
        }
        result.setData(goodsVOList);
        result.setTotal(res.getTotal());
        return  result;
    }
    public DetailResult<List<GoodsPrice>> getGoodsPriceByGoodsId(User user ,Integer goodsId) throws Exception{
        DetailResult<List<GoodsPrice>> result = new DetailResult<>(ResCode.SUCCESS);
        GoodsPriceSearchDto searchDto = new GoodsPriceSearchDto();
        searchDto.setGoodsId(goodsId);
        searchDto.setState((byte)1);
        List<GoodsPrice> list= managerService.searchGoodsPrice(searchDto).getData();
        if(list!=null&&!list.isEmpty()){
            list = getCurrentUserGoodsprice(list,user);
            result.setData(list);
            return result;
        }
        return result;


    }


    /**
     * 商品价格和用户进行转换
     * @param list
     * @param user
     * @return
     * @throws Exception
     */
    List<GoodsPrice> getCurrentUserGoodsprice(List<GoodsPrice> list,User user)throws Exception{
        if(list==null||list.isEmpty())return list;
        if(user==null){
            // 用户未登录，购物车数量全部为0,实际价就是零售价
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setBuyPrice(0);
            }
        }else {
            // 用户已登录，需要查询购物车里规格数量，以及换算实际价
            UserPrivilege userPrivilege = managerService.getUserPrivilegeByUser(user.getUserId()).getData();
            int powerPF = userPrivilege.getIsWholesaleprice();// 享受批发价
            int rebate = userPrivilege.getDiscount();// 折扣
            for (int y = 0; y < list.size(); y++) {
                int retailPrice = 0;
                if (powerPF == 1) {
                    // 享受批发价
                    retailPrice = list.get(y).getWholesalePrice();
                } else {
                    // 不享受批发价
                    retailPrice = list.get(y).getRetailPrice();
                }

                list.get(y)
                        .setRetailPrice(retailPrice * rebate / 100);// 零售价再乘以折扣
                // 获取购物车中的数量
                int count = 0;
                ShoppingCart shoppingCart = managerService
                        .queryShoppingCartByUserIdAndPriceId(user.getUserId(),list.get(y).getPriceId()).getData();
                if (shoppingCart != null) {
                    count = shoppingCart.getQuantity();
                }
                list.get(y).setBuyPrice(count);
            }
        }
        return  list;

    }

}
