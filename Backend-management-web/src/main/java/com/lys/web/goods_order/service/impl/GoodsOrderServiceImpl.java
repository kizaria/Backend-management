package com.lys.web.goods_order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.chart.entity.orderChart;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.entity.MyGoodsParm;
import com.lys.web.goods.service.GoodsService;
import com.lys.web.goods_order.entity.GoodsOrder;
import com.lys.web.goods_order.entity.OrderParm;
import com.lys.web.goods_order.entity.OrderVo;
import com.lys.web.goods_order.mapper.GoodsOrderMapper;
import com.lys.web.goods_order.service.GoodsOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
* @author kiza
* @description 针对表【goods_order】的数据库操作Service实现
* @createDate 2024-01-23 21:38:39
*/
@Service
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderMapper, GoodsOrder>
    implements GoodsOrderService {

    @Autowired
    private GoodsService goodsService;
    @Override
    @Transactional
    public boolean replaceOrder(GoodsOrder goodsOrder) {
        LambdaQueryWrapper<GoodsOrder> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsOrder::getGoodsId,goodsOrder.getGoodsId());
        //查询
        GoodsOrder i=this.baseMapper.selectOne(queryWrapper);
        if(i!=null){
            return false;
        }
        //插入订单数据
        goodsOrder.setCreateTime(new Date());
        int insert=this.baseMapper.insert(goodsOrder);
        //更新状态
        if(insert>0){
            LambdaUpdateWrapper<Goods> updateWrapper=new LambdaUpdateWrapper<>();
            updateWrapper.set(Goods::getSellStatus,"1")
                    .set(Goods::getStatus,"1")
                    .eq(Goods::getGoodsId,goodsOrder.getGoodsId())
                    .eq(Goods::getSellStatus,"0")
                    .eq(Goods::getStatus,"0");
            goodsService.update(updateWrapper);
        }
        return true;
    }

    @Override
    public Page<Goods> getMyOrder(MyGoodsParm parm) {
        Page<Goods> page=new Page<>(parm.getCurrentPage(),parm.getPageSize());
        return this.baseMapper.getMyOrder(page,parm.getUserId());
    }

    @Override
    public Page<Goods> getSellOrder(MyGoodsParm parm) {
        Page<Goods> page=new Page<>(parm.getCurrentPage(),parm.getPageSize());
        return this.baseMapper.getSellOrder(page,parm.getUserId());
    }

    @Override
    public Page<OrderVo> getList(OrderParm parm) {
        Page<OrderVo> page=new Page<>(parm.getCurrentPage(),parm.getPageSize());
        return this.baseMapper.getList(page,parm.getGoodsName());
    }

    @Override
    public List<orderChart> getorderChart() {
        return this.baseMapper.getorderChart();
    }
}




