package com.lys.web.home.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lys.annotation.Auth.Auth;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.service.GoodsService;
import com.lys.web.goods_category.entity.GoodsCategory;
import com.lys.web.goods_category.service.GoodsCategoryService;
import com.lys.web.goods_report.entity.GoodsReport;
import com.lys.web.goods_report.service.GoodsReportService;
import com.lys.web.home.entity.ChartVo;
import com.lys.web.home.entity.HomeVo;
import com.lys.web.wx_user.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private GoodsReportService goodsReportService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @GetMapping("/getTotal")
    @Auth
    public ResultVo getTotal(){
        HomeVo vo = new HomeVo();
        //查询待处理数量
        LambdaQueryWrapper<GoodsReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsReport::getStatus,"0");
        int ReTotal = goodsReportService.count(queryWrapper);
        vo.setReTotal(ReTotal);
        //查询注册数量
        int UserTotal = wxUserService.count();
        vo.setUserTotal(UserTotal);
        //查询闲置商品数量
        LambdaQueryWrapper<Goods> unqueryWrapper = new LambdaQueryWrapper<>();
        unqueryWrapper.eq(Goods::getType,"0");
        int UnTotal = goodsService.count(unqueryWrapper);
        vo.setUnTotal(UnTotal);
        //查询求购商品数量
        LambdaQueryWrapper<Goods> byqueryWrapper = new LambdaQueryWrapper<>();
        byqueryWrapper.eq(Goods::getType,"1");
        int ByTotal = goodsService.count(byqueryWrapper);
        vo.setByTotal(ByTotal);
        return ResultUtils.success("查询成功",vo);
    }

    /**
     * 商品饼状图，包括首页，销售分析，需要登录，无需log
     * @return
     */
    @GetMapping("/getChart")
    @Auth
    public ResultVo getChart(){
        List<ChartVo> list = goodsService.getList();
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 商品饼状图，包括首页，销售分析，需要登录，无需log
     * @return
     */
    @GetMapping("/getChartName")
    @Auth
    public ResultVo getChartName(){
        List<GoodsCategory> list = goodsCategoryService.list();
        Set<String> collect = list.stream().map(GoodsCategory::getCategoryName).collect(Collectors.toSet());

        return ResultUtils.success("查询成功",collect);
    }

}
