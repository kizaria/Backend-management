package com.lys.web.goods_help.entity;

import lombok.Data;

/**
 * 用于接收后台 求购管理 实体类
 */

@Data
public class HelpParmAdmin {
    private Long currentPage;
    private Long pageSize;
    private String title;
}