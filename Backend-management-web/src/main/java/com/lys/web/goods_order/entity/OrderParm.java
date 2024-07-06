package com.lys.web.goods_order.entity;


import lombok.Data;

/**
 * 后台订单管理，所需实体类
 */
@Data
public class OrderParm {
    private Long currentPage;
    private Long pageSize;
    private String goodsName;
}
