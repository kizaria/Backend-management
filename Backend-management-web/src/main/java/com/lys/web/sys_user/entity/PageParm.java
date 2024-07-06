package com.lys.web.sys_user.entity;

import lombok.Data;

/**
 * 后台管理员列表查询，所需接收参数
 */
@Data
public class PageParm {
    private Long currentPage;
    private Long pageSize;
    private String nickName;
}
