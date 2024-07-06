package com.lys.web.goods.controller;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.goods.entity.*;
import com.lys.web.goods.service.GoodsService;
import com.lys.web.goods_category.entity.GoodsCategory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 前端用户发布商品，需要登录，无需log
     * @param goods
     * @return
     */
    //前端用户发布商品
    @PostMapping("/release")
    @Auth
    public ResultVo  release(@RequestBody Goods goods){
        //设置时间
        goods.setCreateTime(new Date());
        if(goodsService.save(goods)){
            return ResultUtils.success("发布成功,等待管理员审核");
        }else{
            return ResultUtils.error("发布失败！");
        }

    }


    /**
     * 前端后端用来修改商品
     * 包括商品上架下架，审核状态全部依赖edit
     * 需要登录
     * @param goods
     * @return
     */
    //更新商品
    @PutMapping("/edit")
    @Auth
    @HoneyLogs(operation = "商品",type = LogType.UPDATE)
    public ResultVo edit(@RequestBody Goods goods){

        if(goodsService.updateById(goods)){
            return ResultUtils.success("编辑成功");
        }else{
            return ResultUtils.error("编辑失败");
        }
    }

    /**
     * 后台商品列表，需要登录，无需log
     * @param goodsListParm
     * @return
     */
    //后台 商品列表 展示全部(不包括逻辑删除的)
    @GetMapping("/list")
    @Auth
    public ResultVo getList(GoodsListParm goodsListParm){
        //构造分页对象
        Page<Goods> page = new Page<>(goodsListParm.getCurrentPage(), goodsListParm.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<Goods> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(goodsListParm.getGoodsName()),Goods::getGoodsName,goodsListParm.getGoodsName())
                .orderByAsc(Goods::getCreateTime)
                .eq(Goods::getDeleteStatus,"0")
                .eq(StringUtils.isNotEmpty(goodsListParm.getCategoryId()),Goods::getCategoryId,goodsListParm.getCategoryId());

        Page<Goods> list = goodsService.page(page, queryWrapper);
        return ResultUtils.success("查询成功",list);

    }

    /**
     * 原小程序api，现已废除
     * @param upandDownParm
     * @return
     */
    //上架下架
    @PostMapping("/upanddown")
    @Auth
    @HoneyLogs(operation = "商品",type = LogType.UPDATE)
    public ResultVo upanddown(@RequestBody UpandDownParm upandDownParm){
//        //清空redis缓存
//        String key="redis_goods";
//        redisTemplate.delete(key);
        LambdaUpdateWrapper<Goods> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(Goods::getStatus,upandDownParm.getStatus())
                .eq(Goods::getGoodsId,upandDownParm.getGoodsId());
        if(goodsService.update(queryWrapper)){
            return ResultUtils.success("设置成功");
        }else{
            return ResultUtils.error("设置失败");
        }
    }

    /**
     *后台设置商品是否推荐首页，需要登录，需要log
     * @param upandDownParm
     * @return
     */
    //后台推荐首页
    @PostMapping("/setIndex")
    @Auth
    @HoneyLogs(operation = "商品",type = LogType.UPDATE)
    public ResultVo setIndex(@RequestBody UpandDownParm upandDownParm){
        LambdaUpdateWrapper<Goods> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(Goods::getSetIndex,upandDownParm.getSetIndex())
                .eq(Goods::getGoodsId,upandDownParm.getGoodsId());
        if(goodsService.update(queryWrapper)){
            return ResultUtils.success("设置成功");
        }else{
            return ResultUtils.error("设置失败");
        }
    }


    /**
     * 前台后台公用的逻辑删除，需要登录，需要log
     * @param upandDownParm
     * @return
     */
    //前台后台使用的逻辑删除
    @PostMapping("/delete")
    @Auth
    @HoneyLogs(operation = "商品",type = LogType.DELETE)
    public ResultVo delete(@RequestBody UpandDownParm upandDownParm){
        LambdaUpdateWrapper<Goods> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(Goods::getDeleteStatus,"1")
                .eq(Goods::getGoodsId,upandDownParm.getGoodsId());
        if(goodsService.update(queryWrapper)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }

    /**
     * 原小程序api，现已废除
     * 新首页推荐api转到banner模块
     * @param parm
     * @return
     */
    //小程序首页列表查询
    @GetMapping("/getIndexList")
    @Auth
    public ResultVo getIndexList( WxIndexParm parm){
        //构造分页对象
        Page<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<Goods> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(parm.getKeywords()),Goods::getGoodsName,parm.getKeywords())
                .eq(Goods::getStatus,"0")
                .eq(Goods::getDeleteStatus,"0")
                .eq(Goods::getSetIndex,"1")
                .eq(Goods::getSellStatus,"0")
                .orderByDesc(Goods::getCreateTime);

        Page<Goods> list=goodsService.page(page,queryWrapper);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 新用于前台闲置商品页面
     * @param parm
     * @return
     */
    //小程序闲置列表查询 用于闲置商品分类商品列表
    @GetMapping("/getUsedList")
    public ResultVo getUsedList( WxIndexParm parm){
        //构造查询条件
        LambdaQueryWrapper<Goods> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(parm.getKeywords()),Goods::getGoodsName,parm.getKeywords())
                .eq(Goods::getStatus,"0")
                .eq(Goods::getDeleteStatus,"0")
                .eq(Goods::getSellStatus,"0")
                .eq(Goods::getAuditing,"2")
                .eq(StringUtils.isNotEmpty(parm.getCategoryId()),Goods::getCategoryId,parm.getCategoryId())
                .orderByDesc(Goods::getCreateTime);

        List<Goods> list=goodsService.list(queryWrapper);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 原小程序api，现已废除
     * @param parm
     * @return
     */
    //小程序求购列表查询
    @GetMapping("/getBuyList")
    public ResultVo getBuyList( WxIndexParm parm){
        //构造分页对象
        Page<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<Goods> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(parm.getKeywords()),Goods::getGoodsName,parm.getKeywords())
                .eq(Goods::getStatus,"0")
                .eq(Goods::getDeleteStatus,"0")
                .eq(Goods::getType,"1")
                .eq(Goods::getSellStatus,"0")
                .eq(StringUtils.isNotEmpty(parm.getCategoryId()),Goods::getCategoryId,parm.getCategoryId())
                .orderByDesc(Goods::getCreateTime);
        Page<Goods> list=goodsService.page(page,queryWrapper);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 新用于前台我的闲置里
     * @param parm
     * @return
     */
    //小程序我的闲置||我的求购
    @GetMapping("/getMyUnusedList")
    @Auth
    public ResultVo getMyUnusedList(MyGoodsParm parm){
        //构造分页对象
        Page<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<Goods> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getUserId,parm.getUserId())
                .eq(Goods::getDeleteStatus,"0");
        Page<Goods> list=goodsService.page(page,queryWrapper);
        return ResultUtils.success("查询成功",list);
    }

    //导出数据
    @GetMapping("/export")
    @Auth
    @HoneyLogs(operation = "商品",type = LogType.EXPORT)
    public void exportData(HttpServletResponse response) throws IOException {

        ExcelWriter writer = ExcelUtil.getWriter(true);

        //获取数据库数据
        List<GoodsExcel> goodsExcels = goodsService.exportGoods();
        System.out.println("goodsExcels.toString()");
        System.out.println(goodsExcels.toString());
        //写入数据
        writer.write(goodsExcels, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("商品信息表", "UTF-8") + ".xlsx");
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //把数据刷新到输出流里面
        writer.flush(outputStream,true);
        writer.close();
        outputStream.flush();
        outputStream.close();


    }


    /**
     * 前台首页新鲜好物，无需登录，无需log
     * @return
     */
    //首页新鲜好物
    @GetMapping("/GoodsNew")
    public ResultVo GoodsNew(){
        //构造分页对象
        Page<Goods> page = new Page<>(1, 4);

        //构造查询条件
        LambdaQueryWrapper<Goods> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Goods::getCreateTime)
                .eq(Goods::getStatus,"0")
                .eq(Goods::getDeleteStatus,"0")
                .eq(Goods::getAuditing,"2");
        Page<Goods> list=goodsService.page(page,queryWrapper);
        return ResultUtils.success("查询成功",list);
    }


    /**
     *前台根据商品id查询商品，无需登录，无需log
     * @param Id
     * @return
     */
    //前台根据id查询
    @GetMapping("/getGoodsById")
    public ResultVo getGoodsById(String Id){
        Goods goods=goodsService.getById(Id);
        if(goods!=null){
            return ResultUtils.success("查询成功",goods);
        }else {
            return ResultUtils.error("查询失败");
        }
    }

    /**
     * 前台商品详情里的图片,无需登录，无需log
     * @param Id
     * @return
     */
    //商品详情里的图片
    @GetMapping("/getImage")
    public ResultVo getImage(String Id){
        Goods goods=goodsService.getById(Id);
        String image = goods.getImage();
        List<String> strList1 = Arrays.asList(image.split(","));

        return ResultUtils.success("查询成功",strList1);
    }


    /**
     * 前台搜索框，无需登录，无需log
     * @param name
     * @return
     */
    //搜索结果
    @GetMapping("/searchGoods")
    public ResultVo searchGoods(String name){
        //构造查询条件
        LambdaQueryWrapper<Goods> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Goods::getGoodsName,name)
                .eq(Goods::getStatus,"0")
                .eq(Goods::getDeleteStatus,"0")
                .eq(Goods::getSellStatus,"0")
                .eq(Goods::getAuditing,"2")
                .orderByDesc(Goods::getCreateTime);
        List<Goods> list = goodsService.list(queryWrapper);
        return ResultUtils.success("查询成功",list);
    }


}
