package com.lys.web.wx_user.entity;

import lombok.Data;

/**
 * 后台用户列表接收所需实体类
 */
@Data
public class WxUserParm {
    private Long currentPage;
    private Long pageSize;
    private String name;
}
