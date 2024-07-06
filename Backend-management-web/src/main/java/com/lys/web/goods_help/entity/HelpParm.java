package com.lys.web.goods_help.entity;


import lombok.Data;

/**
 * 用于接收前  我的求购 实体类
 */
@Data
public class HelpParm {
    private Long userId;
    private Long currentPage;
    private Long pageSize;
}
