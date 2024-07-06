package com.lys.web.goods_category.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.goods_category.entity.GoodsCategory;
import com.lys.web.goods_category.entity.ListParm;
import com.lys.web.goods_category.entity.SelectType;
import com.lys.web.goods_category.service.GoodsCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;


    /**
     * 后台新增商品分类，需要登录，需要log
     * @param GoodsCategory
     * @return
     */
    //新增分类
    @PostMapping
    @Auth
    @HoneyLogs(operation = "商品分类",type = LogType.ADD)
    public ResultVo add(@RequestBody GoodsCategory GoodsCategory){
        if(goodsCategoryService.save(GoodsCategory)){
            return ResultUtils.success("新增成功");
        }else{
            return ResultUtils.error("新增失败");

        }
    }

    /**
     * 后台编辑商品分类，需要登录，需要log
     * @param goodsCategory
     * @return
     */
    //更新商品分类
    @PutMapping
    @Auth
    @HoneyLogs(operation = "商品分类",type = LogType.UPDATE)
    public ResultVo edit(@RequestBody GoodsCategory goodsCategory){
        if(goodsCategoryService.updateById(goodsCategory)){
            return ResultUtils.success("编辑成功");
        }else{
            return ResultUtils.error("编辑失败");
        }
    }

    /**
     * 后台删除商品分类，需要登录，需要log
     * @param categoryId
     * @return
     */
    //删除商品分类
    @DeleteMapping("/{categoryId}")
    @Auth
    @HoneyLogs(operation = "商品分类",type = LogType.DELETE)
    public ResultVo delete(@PathVariable Long categoryId){
        if(goodsCategoryService.removeById(categoryId)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }


    /**
     * 后台商品分类列表，需要登录，无需log
     * @param pageParm
     * @return
     */
    //商品分类列表
    @GetMapping("/list")
    @Auth
    public ResultVo List(ListParm pageParm){
        //构造分页对象
        Page<GoodsCategory> page = new Page<>(pageParm.getCurrentPage(), pageParm.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<GoodsCategory> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(pageParm.getCategoryName()),GoodsCategory::getCategoryName,pageParm.getCategoryName())
                .orderByAsc(GoodsCategory::getOrderNum);

        Page<GoodsCategory> list=goodsCategoryService.page(page,queryWrapper);
        return ResultUtils.success("查询成功",list);

    }

    /**
     * 原小程序api，现已废弃
     * @return
     */
    //专门为小程序做的分类
    @GetMapping("/WxSelectList")
    @Auth
    public ResultVo WxSelectList(){
        //构造分类列表
        LambdaQueryWrapper<GoodsCategory> queryWrapper=new LambdaQueryWrapper<>();
        //排序
        queryWrapper.orderByAsc(GoodsCategory::getOrderNum);
        List<GoodsCategory> list =goodsCategoryService.list(queryWrapper);
        //存储小程序需要的类型
        List<SelectType> selectList =new ArrayList<>();
        //构造小程序需要的类型
        //ofNullable用来判断list是否空，空的话执行orElse，在通过foreach循环
        Optional.ofNullable(list).orElse(new ArrayList<>()).forEach(item->{
            SelectType type=new SelectType();
            type.setLabel(item.getCategoryName());
            type.setValue(item.getCategoryId());
            selectList.add(type);
        });
        return ResultUtils.success("查询成功",selectList);

    }

    /**
     * 前台获取商品分类，用于闲置商品页面，无需登录，无需log
     * @return
     */
    //小程序分类接口
    @GetMapping("/getCateList")
    public ResultVo getCateList(){
        List<GoodsCategory> list=goodsCategoryService.list();
        return ResultUtils.success("查询成功",list);
    }

}
