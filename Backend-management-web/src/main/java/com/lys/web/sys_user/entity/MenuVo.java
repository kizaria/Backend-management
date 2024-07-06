package com.lys.web.sys_user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 后台管理员登录成功返回的数据的一部分
 */

@Data
@AllArgsConstructor
public class MenuVo {
    private Long menuId;
    private String title;
    private String path;
    private String icon;
}
