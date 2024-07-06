package com.lys.web.sys_banner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.lys.web.goods.entity.Goods;
import lombok.Data;

/**
 * 
 * @TableName sys_banner
 */
@TableName(value ="sys_banner")
@Data
public class SysBanner implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long banId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片路径
     */
    private String images;

    /**
     * 0：上架  1：下架
     */
    private String status;

    /**
     * 序号
     */
    private Integer orderNum;

    @TableField(exist = false)
    private Goods goods;

}