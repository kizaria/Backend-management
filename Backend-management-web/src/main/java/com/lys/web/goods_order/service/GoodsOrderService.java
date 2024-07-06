package com.lys.web.goods_order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lys.web.chart.entity.orderChart;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.entity.MyGoodsParm;
import com.lys.web.goods_order.entity.GoodsOrder;
import com.lys.web.goods_order.entity.OrderParm;
import com.lys.web.goods_order.entity.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods_order】的数据库操作Service
* @createDate 2024-01-23 21:38:39
*/
public interface GoodsOrderService extends IService<GoodsOrder> {
    boolean replaceOrder(GoodsOrder goodsOrder);
    //查询我的订单
    Page<Goods> getMyOrder(MyGoodsParm parm);
    //查询出售订单
    Page<Goods> getSellOrder(MyGoodsParm parm);

    //查询订单
    Page<OrderVo> getList(OrderParm parm);


    //订单分析图
    List<orderChart> getorderChart();

}
