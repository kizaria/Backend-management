package com.lys.web.goods_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.web.chart.entity.orderChart;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_order.entity.GoodsOrder;
import com.lys.web.goods_order.entity.OrderVo;
import com.lys.web.home.entity.ChartVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods_order】的数据库操作Mapper
* @createDate 2024-01-23 21:38:39
* @Entity generator.domain.GoodsOrder
*/

@Mapper
public interface GoodsOrderMapper extends BaseMapper<GoodsOrder> {
    //查询我的订单
    Page<Goods> getMyOrder(Page<Goods> page,@Param("userId") Long userId);

    //查询出售订单
    Page<Goods> getSellOrder(Page<Goods> page,@Param("userId") Long userId);


    //查询订单
    Page<OrderVo> getList(Page<OrderVo> page,@Param("goodsName") String goodsName);

    //订单分析图
    List<orderChart> getorderChart();
}




