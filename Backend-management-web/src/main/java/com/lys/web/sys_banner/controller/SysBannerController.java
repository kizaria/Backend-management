package com.lys.web.sys_banner.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.service.GoodsService;
import com.lys.web.goods_category.entity.SelectType;
import com.lys.web.sys_banner.entity.BannerParm;
import com.lys.web.sys_banner.entity.HomeBanner;
import com.lys.web.sys_banner.entity.SysBanner;
import com.lys.web.sys_banner.service.SysBannerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/banner")
public class SysBannerController {
    @Autowired
    private SysBannerService sysBannerService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 后台新增首页推荐，需要登录，需要log
     * @param sysBanner
     * @return
     */
    //新增首页推荐
    @PostMapping
    @Auth
    @HoneyLogs(operation = "商品推荐",type = LogType.ADD)
    public ResultVo add(@RequestBody SysBanner sysBanner){
        if(sysBannerService.save(sysBanner)){
            return ResultUtils.success("新增成功");
        }else{
            return ResultUtils.error("新增失败");

        }
    }

    /**
     * 后台更改首页推荐，需要登录，需要log
     * @param sysBanner
     * @return
     */
    //更新
    @PutMapping
    @Auth
    @HoneyLogs(operation = "商品推荐",type = LogType.UPDATE)
    public ResultVo edit(@RequestBody SysBanner sysBanner){
        if(sysBannerService.updateById(sysBanner)){
            return ResultUtils.success("编辑成功");
        }else{
            return ResultUtils.error("编辑失败");
        }
    }

    /**
     * 后台删除首页推荐，需要登录，需要log
     * @param banId
     * @return
     */
    //删除
    @DeleteMapping("/{banId}")
    @Auth
    @HoneyLogs(operation = "商品推荐",type = LogType.DELETE)
    public ResultVo delete(@PathVariable Long banId){
        if(sysBannerService.removeById(banId)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }


    /**
     * 后台获取所有推荐信息，需要登录，无需log
     * @param parm
     * @return
     */
    //列表
    @GetMapping("/list")
    @Auth
    public ResultVo List(BannerParm parm){
        //构造分页对象
        Page<SysBanner> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<SysBanner> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(parm.getTitle()),SysBanner::getTitle,parm.getTitle())
                .orderByAsc(SysBanner::getOrderNum);

        Page<SysBanner> list=sysBannerService.page(page,queryWrapper);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 后台新建推荐时，获取商品id和名字组合，需要登录  ，无需log
     * @return
     */
    //首页推荐需要的商品id
    @GetMapping("/bannerGetGoods")
    @Auth
    public ResultVo bannerGetGoods(){
        //查询商品
        LambdaQueryWrapper<Goods> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getStatus,"0")
                    .eq(Goods::getSellStatus,"0")
                    .eq(Goods::getDeleteStatus,"0");
        List<Goods> list=goodsService.list(queryWrapper);
        //组装数据
        List<HomeBanner> selectTypeList=new ArrayList<>();
        for (Goods goods : list) {
            HomeBanner type = new HomeBanner();
            type.setValue(goods.getGoodsId());
            type.setLabel(goods.getGoodsName());
            selectTypeList.add(type);
        }
        return ResultUtils.success("查询成功",selectTypeList);
    }


    /**
     * 后台设置该推荐是否可用，需要登录，需要log
     * @param sysBanner
     * @return
     */
    //修改状态
    @PostMapping("/upanddown")
    @Auth
    @HoneyLogs(operation = "商品推荐",type = LogType.UPDATE)
    public ResultVo upanddown(@RequestBody SysBanner sysBanner){
        LambdaUpdateWrapper<SysBanner> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(SysBanner::getStatus,sysBanner.getStatus())
                     .eq(SysBanner::getBanId,sysBanner.getBanId());
        if(sysBannerService.update(updateWrapper)){
            return ResultUtils.success("设置成功");
        }else{
            return ResultUtils.error("设置失败");
        }

    }


    /**
     * 现为前台首页数据
     * @return
     */
    //查询小程序首页轮播图数据
    @GetMapping("/getIndexBanner")
    public ResultVo getIndexBanner(){
        LambdaQueryWrapper<SysBanner> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysBanner::getStatus,"0")
                .orderByAsc(SysBanner::getOrderNum);
        List<SysBanner> list = sysBannerService.list(queryWrapper);
        if(!list.isEmpty()){
            for (SysBanner sysBanner : list) {
                Long goodsId = sysBanner.getGoodsId();
                Goods goods = goodsService.getById(goodsId);
                sysBanner.setGoods(goods);
            }
        }
        return ResultUtils.success("查询成功",list);
    }





}
