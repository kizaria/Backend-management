package com.lys.web.goods.entity;


import lombok.Data;

/**
 * 现用于前台 闲置页面 keywords已用不到
 */
@Data
public class WxIndexParm {
    private Long currentPage;
    private Long pageSize;
    private String categoryId;
    private String keywords;
}
