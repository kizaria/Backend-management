package com.lys.web.goods_collect.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.service.GoodsService;
import com.lys.web.goods_collect.entity.CollectParm;
import com.lys.web.goods_collect.entity.GoodsCollect;
import com.lys.web.goods_collect.service.GoodsCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/collect")
public class GoodsCollectController {

    @Autowired
    private GoodsCollectService goodsCollectService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 前台收藏商品，需要登录，无需log
     * @param goodsCollect
     * @return
     */
    //收藏商品
    @PostMapping("/collect")
    @Auth
    public ResultVo collect(@RequestBody GoodsCollect goodsCollect){
        //判断是否收藏
        LambdaQueryWrapper<GoodsCollect> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCollect::getGoodsId,goodsCollect.getGoodsId())
                .eq(GoodsCollect::getUserId,goodsCollect.getUserId());
        GoodsCollect one = goodsCollectService.getOne(queryWrapper);
        if(one==null){//为收藏
            goodsCollect.setCollectTime(new Date());
            if(goodsCollectService.save(goodsCollect)){
                return ResultUtils.success("收藏成功");
            }else {
                return ResultUtils.error("收藏失败");
            }

        }else {//取消收藏
            if(goodsCollectService.remove(queryWrapper)){
                return ResultUtils.success("取消成功");
            }else {
                return ResultUtils.error("取消失败");
            }
        }
    }

    /**
     * 判断用户是否收藏商品，需要登录，无需log
     * @param goodsCollect
     * @return
     */
    //判断是否收藏
    @GetMapping("/hasCollect")
    @Auth
    public ResultVo hasCollect( GoodsCollect goodsCollect){
        //判断是否收藏
        LambdaQueryWrapper<GoodsCollect> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCollect::getGoodsId,goodsCollect.getGoodsId())
                .eq(GoodsCollect::getUserId,goodsCollect.getUserId());
        GoodsCollect one = goodsCollectService.getOne(queryWrapper);
        if(one!=null){
            return ResultUtils.success("查询成功",1);
        }else {
            return ResultUtils.success("查询成功",0);
        }
    }

    /**
     * 现为前端我的收藏，需要登录，无需log
     * @param parm
     * @return
     */
    //小程序我的收藏列表
    @GetMapping("/getMyCollect")
    @Auth
    public ResultVo getMyCollect(CollectParm parm){
        Page<Goods> list =goodsCollectService.getMyCollect(parm);
        return ResultUtils.success("查询成功",list);
    }

    /***
     *前台首页，收藏推荐，无需登录，无需log
     * @return
     */
    //首页人气推荐
    @GetMapping("/GoodsHot")
    public ResultVo GoodsHot(){
        List<Goods> sumCollect = goodsCollectService.getSumCollect();
        List<Goods> list = new ArrayList<>();
        for (Goods goods : sumCollect) {
            LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Goods::getGoodsId,goods.getGoodsId());
            Goods one = goodsService.getOne(queryWrapper);
            list.add(one);
            if(list.size()==4){
                return ResultUtils.success("查询成功",list);
            }
        }
        return ResultUtils.success("查询成功",list);
    }


    /**
     * 商品详情里的收藏人数，无需登录，无需log
     * @param goodsId
     * @return
     */
    //收藏人数
    @GetMapping("/getCount")
    public ResultVo getCount(Long goodsId){
        Integer count = goodsCollectService.getCount(goodsId);
        return ResultUtils.success("查询成功",count);
    }



}
