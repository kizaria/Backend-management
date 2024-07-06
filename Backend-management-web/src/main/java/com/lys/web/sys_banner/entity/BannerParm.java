package com.lys.web.sys_banner.entity;

import lombok.Data;

/**
 * 后台接收所需数据
 */
@Data
public class BannerParm {
    private Long currentPage;
    private Long pageSize;
    private String title;
}
