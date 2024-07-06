package com.lys.web.goods_category.entity;

import lombok.Data;
/**
 * 用于接收后台 分类管理 实体类
 */
@Data
public class ListParm {
    private Long currentPage;
    private Long pageSize;
    private String categoryName;
}
