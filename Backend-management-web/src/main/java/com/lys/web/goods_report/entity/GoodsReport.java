package com.lys.web.goods_report.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName goods_report
 */
@TableName(value ="goods_report")
@Data
public class GoodsReport{
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long reportId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 举报人id
     */
    private Long reportUser;

    /**
     * 举报原因
     */
    private String reason;

    /**
     * 举报时间
     */
    private Date reportTime;

    /**
     * 状态 0：未处理 1：已处理
     */
    private String status;
}