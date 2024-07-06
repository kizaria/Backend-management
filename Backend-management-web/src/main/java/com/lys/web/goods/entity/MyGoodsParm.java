package com.lys.web.goods.entity;

import lombok.Data;

/**
 * 前台跳转闲置需要的实体类
 */
@Data
public class MyGoodsParm {
    private Long userId;
    //类型 0：闲置 1：求购
    private String type;
    private Long currentPage;
    private Long pageSize;
}
