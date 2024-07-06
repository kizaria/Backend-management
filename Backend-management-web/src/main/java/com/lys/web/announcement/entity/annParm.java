package com.lys.web.announcement.entity;

import lombok.Data;

/**
 * 用于接收后台 公告管理 传来的数据
 */
@Data
public class annParm {
    private Long currentPage;
    private Long pageSize;
    private String title;
}