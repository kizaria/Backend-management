package com.lys.web.goods_order.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.chat.WebSocketSingleServer;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.entity.MyGoodsParm;
import com.lys.web.goods.service.GoodsService;
import com.lys.web.goods_order.entity.GoodsOrder;
import com.lys.web.goods_order.entity.OrderParm;
import com.lys.web.goods_order.entity.OrderVo;
import com.lys.web.goods_order.service.GoodsOrderService;
import com.lys.web.notice.entity.Notice;
import com.lys.web.notice.service.NoticeService;
import com.lys.web.wx_user.entity.WxUser;
import com.lys.web.wx_user.service.WxUserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/goodsOrder")
public class GoodsOrderController {
    @Autowired
    private GoodsOrderService goodsOrderService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private NoticeService noticeService;

    /**
     * 前台下单，需要登录，无需log
     * @param goodsOrder
     * @return
     */
    //交易订单
    @PostMapping("/replaceOrder")
    @Auth
    public ResultVo replaceOrder(@RequestBody GoodsOrder goodsOrder){
        if(goodsOrderService.replaceOrder(goodsOrder)){
            LambdaQueryWrapper<GoodsOrder> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(GoodsOrder::getGoodsId,goodsOrder.getGoodsId());
            GoodsOrder one = goodsOrderService.getOne(queryWrapper);
            return ResultUtils.success("交易成功",one);
        }else {
            return ResultUtils.error("交易失败");
        }
    }

    /**
     * 现为前台我的订单里的我的订单，需要登录，无需log
     * @param parm
     * @return
     */
    //小程序我的订单
    @GetMapping("/getMyOrder")
    @Auth
    public ResultVo getMyOrder(MyGoodsParm parm){
        Page<Goods> list =goodsOrderService.getMyOrder(parm);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 现为前台我的订单里的出售订单，需要登录，无需log
     * @param parm
     * @return
     */
    //小程序出售订单
    @GetMapping("/getSellOrder")
    @Auth
    public ResultVo getSellOrder(MyGoodsParm parm){
        Page<Goods> list =goodsOrderService.getSellOrder(parm);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 现为前台我的订单里的删除订单，需要登录，无需log
     * @param goodsOrder
     * @return
     */
    //小程序删除订单
    @PostMapping("/deleteOrder")
    @Auth
    public ResultVo deleteOrder(@RequestBody GoodsOrder goodsOrder){
        LambdaUpdateWrapper<GoodsOrder> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(GoodsOrder::getDeleteStatus,"1")
                .eq(GoodsOrder::getOrderId,goodsOrder.getOrderId());
        if(goodsOrderService.update(updateWrapper)){
            return ResultUtils.success("删除成功!");
        }else {
            return ResultUtils.error("删除失败!");
        }
    }

    /**
     * 后台 订单管理 列表 ，需要登录，无需log
     * @param parm
     * @return
     */
    //订单列表
    @GetMapping("/getList")
    @Auth
    public ResultVo getList(OrderParm parm){
        Page<OrderVo> list=goodsOrderService.getList(parm);
        for(int i=0;i<list.getRecords().size();i++){
            WxUser one = wxUserService.getById(list.getRecords().get(i).getUserId());
            list.getRecords().get(i).setGName(one.getName());
        }
        return ResultUtils.success("查询成功",list);
    }


    /**
     * 后台 订单管理 编辑 ，需要登录，需要log
     * @param goodsOrder
     * @return
     */
    //编辑订单
    @PostMapping("/edit")
    @Auth
    @HoneyLogs(operation = "商品订单",type = LogType.UPDATE)
    public ResultVo edit(@RequestBody GoodsOrder goodsOrder){
        if(goodsOrderService.updateById(goodsOrder)){
            return ResultUtils.success("编辑成功");
        }else{
            return ResultUtils.error("编辑失败");
        }
    }

    /**
     * 前台和后台共用api，需要登录，无需log
     * @param goodsOrder
     * @return
     */
    //逻辑删除
    @PostMapping("/delete")
    @Auth
//    @HoneyLogs(operation = "商品订单",type = LogType.DELETE)
    public ResultVo delete(@RequestBody GoodsOrder goodsOrder){
        LambdaUpdateWrapper<GoodsOrder> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(GoodsOrder::getDeleteStatus,"1")
                .eq(GoodsOrder::getOrderId,goodsOrder.getOrderId());
        if(goodsOrderService.update(queryWrapper)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }


    /**
     * 前台 我的订单 确认收货，需要登录，无需log
     * @param goodsOrder
     * @return
     */
    //确认收货
    @PostMapping("/confirm")
    @Auth
//    @HoneyLogs(operation = "商品订单",type = LogType.UPDATE)
    public ResultVo Confirm(@RequestBody GoodsOrder goodsOrder){
        LambdaUpdateWrapper<GoodsOrder> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(GoodsOrder::getOrderStatus,"1")
                .eq(GoodsOrder::getOrderId,goodsOrder.getOrderId());
        if(goodsOrderService.update(queryWrapper)){
            return ResultUtils.success("确认成功");
        }else{
            return ResultUtils.error("确认失败");
        }
    }

    /**
     * 前台 我的订单 确认发货，需要登录，无需log
     * @param goodsOrder
     * @return
     */
    //确认收货
    @PostMapping("/confirmReceiving")
    @Auth
//    @HoneyLogs(operation = "商品订单",type = LogType.UPDATE)
    public ResultVo confirmReceiving(@RequestBody GoodsOrder goodsOrder){
        LambdaUpdateWrapper<GoodsOrder> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(GoodsOrder::getOrderReceiving,"1")
                .set(GoodsOrder::getOrderExpress,goodsOrder.getOrderExpress())
                .eq(GoodsOrder::getOrderId,goodsOrder.getOrderId());
        if(goodsOrderService.update(queryWrapper)){
            return ResultUtils.success("确认成功");
        }else{
            return ResultUtils.error("确认失败");
        }
    }

    /**
     * 支付页需要订单id，商品名，价格，需要登录，无需log
     * @param orderId
     * @return
     */
    //根据id查询订单
    @GetMapping("/getOrderById")
    @Auth
    public ResultVo getOrderById(String orderId){
        GoodsOrder one = goodsOrderService.getById(orderId);
        if(one!=null){
            return ResultUtils.success("查询成功！",one);
        }else{
            return ResultUtils.error("查询失败！");
        }
    }


}
