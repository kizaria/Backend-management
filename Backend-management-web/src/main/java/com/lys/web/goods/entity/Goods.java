package com.lys.web.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable{
    /**
     * 商品id
     */
    @TableId(type = IdType.AUTO)
    private Long goodsId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 类别 0：闲置 1：求购
     */
    private String type;

    /**
     * 订单id orderId数据库并不存在
     */
    @TableField(exist = false)
    private String orderId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品简介
     */
    private String goodsDesc;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 实际价格 数据库并不存在
     */
    @TableField(exist = false)
    private BigDecimal price;

    /**
     * 联系人
     */
    private String userName;

    /**
     * 联系人电话
     */
    private String phone;

    /**
     * 微信号
     */
    private String wxNum;

    /**
     * 地址
     */
    private String address;

    /**
     * 图片
     */
    private String image;

    /**
     * 状态 0：上架 1：下架
     */
    private String status;

    /**
     * 0：未出售 1：已出售
     */
    private String sellStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 交易时间 数据库并不存在
     */
//    @TableField(exist = false)
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date sellTime;

    /**
     * 推荐首页 0：未推荐 1：推荐
     */
    private String setIndex;

    /**
     * 逻辑删除 0：未删除 1：删除
     */
    private String deleteStatus;

    /**
     * 交易时间
     */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sellTime;

    /**
     * 订单状态
     */
    @TableField(exist = false)
    private String orderStatus;

    /**
     * 投诉状态
     */
    @TableField(exist = false)
    private String reportStatus;

    /**
     * 投诉原因
     */
    @TableField(exist = false)
    private String reason;

    /**
     * 确认付款  0：未确认 1：确认
     */
    @TableField(exist = false)
    private String orderPay;

    /**
     * 审核
     */
    private String auditing;

    /**
     * 确认发货 0：未确认 1：确认
     */
    @TableField(exist = false)
    private String orderReceiving;

    /**
     * 快递号
     */
    @TableField(exist = false)
    private String orderExpress;

}