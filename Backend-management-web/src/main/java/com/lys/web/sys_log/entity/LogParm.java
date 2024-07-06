package com.lys.web.sys_log.entity;

import lombok.Data;

/**
 * 后台接收所需实体类
 */
@Data
public class LogParm {
    private Long currentPage;
    private Long pageSize;
    private String username;
}
