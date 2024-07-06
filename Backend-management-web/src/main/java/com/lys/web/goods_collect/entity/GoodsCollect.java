package com.lys.web.goods_collect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName goods_collect
 */
@TableName(value ="goods_collect")
@Data
public class GoodsCollect {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long collectId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 收藏时间
     */
    private Date collectTime;
}