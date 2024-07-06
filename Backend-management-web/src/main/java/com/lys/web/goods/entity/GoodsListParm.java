package com.lys.web.goods.entity;

import lombok.Data;

/**
 * 用于接收后台 商品管理 实体类
 */
@Data
public class GoodsListParm {
    private Long currentPage;
    private Long pageSize;
    private String goodsName;
    private String categoryId;
}
