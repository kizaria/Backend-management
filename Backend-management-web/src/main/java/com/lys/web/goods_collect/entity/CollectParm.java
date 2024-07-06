package com.lys.web.goods_collect.entity;


import lombok.Data;

/**
 * 前台 我的收藏 接收实体类
 */
@Data
public class CollectParm {
    private Long userId;
    private Long currentPage;
    private Long pageSize;
}
