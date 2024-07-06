package com.lys.web.goods_order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName goods_order
 */
@TableName(value ="goods_order")
@Data
public class GoodsOrder {
    /**
     * 订单id
     */
    @TableId(type = IdType.NONE	)
    private String orderId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名字
     */
    private String goodsName;

    /**
     * 下单人id
     */
    private Long orderUser;

    /**
     * 订单金额
     */
    private BigDecimal price;

    /**
     * 下单时间
     */
    private Date createTime;
    /**
     * 地址表id
     */
    private Integer addressid;
    /**
     * 逻辑删除
     */
    private String deleteStatus;

    /**
     * 确认收货
     */
    private String orderStatus;

    /**
     * 确认付款  0：未确认 1：确认
     */
    private String orderPay;

    /**
     * 确认发货 0：未确认 1：确认
     */
    private String orderReceiving;

    /**
     * 快递号
     */
    private String orderExpress;


}