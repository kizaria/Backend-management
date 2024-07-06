package com.lys.web.address.entity;


import lombok.Data;

/**
 * 用于接收前台 我的地址 传来的数据
 */
@Data
public class addressParm {
    private Long userId;
    private Long currentPage;
    private Long pageSize;
}
