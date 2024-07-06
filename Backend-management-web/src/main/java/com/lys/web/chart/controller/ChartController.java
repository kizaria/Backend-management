package com.lys.web.chart.controller;

import com.lys.annotation.Auth.Auth;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.chart.entity.orderChart;
import com.lys.web.chart.entity.userChart;
import com.lys.web.goods_category.entity.GoodsCategory;
import com.lys.web.goods_order.service.GoodsOrderService;
import com.lys.web.wx_user.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/chart")
public class ChartController {

    @Autowired
    private GoodsOrderService goodsOrderService;

    @Autowired
    private WxUserService wxUserService;


    //数据图，返回后台管理的订单折线图的订单日期
    @PostMapping("/orderDay")
    @Auth
    public ResultVo orderDay(){
        List<orderChart> orderCharts = goodsOrderService.getorderChart();

        ArrayList<String> day = (ArrayList<String>) orderCharts.stream().map(orderChart::getDay).collect(Collectors.toList());
        ArrayList<String> orders = (ArrayList<String>) orderCharts.stream().map(orderChart::getOrders).collect(Collectors.toList());
        return ResultUtils.success("查询成功",day);
    }

    //数据图，返回后台管理的订单折线图的订单数量
    @PostMapping("/orderOrders")
    @Auth
    public ResultVo orderOrders(){
        List<orderChart> orderCharts = goodsOrderService.getorderChart();
        ArrayList<String> day = (ArrayList<String>) orderCharts.stream().map(orderChart::getDay).collect(Collectors.toList());
        ArrayList<String> orders = (ArrayList<String>) orderCharts.stream().map(orderChart::getOrders).collect(Collectors.toList());
        return ResultUtils.success("查询成功",orders);
    }

    //数据图，返回后台管理的用户柱状图的用户注册日期
    @PostMapping("/userDay")
    @Auth
    public ResultVo userDay(){
        List<userChart> user = wxUserService.getUserChart();

        ArrayList<String> day = (ArrayList<String>) user.stream().map(userChart::getDay).collect(Collectors.toList());
        ArrayList<String> users = (ArrayList<String>) user.stream().map(userChart::getUsers).collect(Collectors.toList());
        return ResultUtils.success("查询成功",day);
    }

    //数据图，返回后台管理的用户柱状图的用户注册数量
    @PostMapping("/userUsers")
    @Auth
    public ResultVo userUsers(){
        List<userChart> user = wxUserService.getUserChart();

        ArrayList<String> day = (ArrayList<String>) user.stream().map(userChart::getDay).collect(Collectors.toList());
        ArrayList<String> users = (ArrayList<String>) user.stream().map(userChart::getUsers).collect(Collectors.toList());
        return ResultUtils.success("查询成功",users);
    }

}
