package com.lys.web.goods.entity;


import lombok.Data;

/**
 * 用于接收后台 商品管理 的商品上架下架和首页推荐
 */
@Data
public class UpandDownParm {
    private Long goodsId;
    private String status;
    private String setIndex;
}
