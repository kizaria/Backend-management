package com.lys.web.HomeGoods.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.HomeGoods.entity.HomeGoods;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.service.GoodsService;
import com.lys.web.goods_category.entity.GoodsCategory;
import com.lys.web.goods_category.entity.SelectType;
import com.lys.web.goods_category.service.GoodsCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/home")
public class HomeGoodsController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 前台首页下面产品，无需登录，无需log
     * @return
     */
    //首页产品
    @GetMapping("/GoodsProduct")
    public ResultVo GoodsProduct(){

        List<HomeGoods> HomeList=new ArrayList<>();
        List<GoodsCategory> list=goodsCategoryService.list();
        for (GoodsCategory goodsCategory : list) {
            HomeGoods homeGoods = new HomeGoods();
            homeGoods.setCategoryId(goodsCategory.getCategoryId());
            homeGoods.setCategoryName(goodsCategory.getCategoryName());
            homeGoods.setImages(goodsCategory.getImages());
            //构造查询条件
            LambdaQueryWrapper<Goods> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(Goods::getCategoryId,goodsCategory.getCategoryId())
                    .eq(Goods::getStatus,"0")
                    .eq(Goods::getDeleteStatus,"0")
                    .eq(Goods::getAuditing,"2");
            homeGoods.setGoodsList(goodsService.list(queryWrapper));
            HomeList.add(homeGoods);
        }

        return ResultUtils.success("查询成功",HomeList);

    }

}
