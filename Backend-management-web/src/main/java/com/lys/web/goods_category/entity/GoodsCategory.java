package com.lys.web.goods_category.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName goods_category
 */
@Data
@TableName(value ="goods_category")
public class GoodsCategory  {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类图片
     */
    private String images;

    /**
     * 序号  用于排序
     */
    private Integer orderNum;

}