package com.lys.web.wx_user.entity;

import lombok.Data;

/**
 * 前台用户登录成功返回的数据
 */
@Data
public class LoginVo {
    private Long userId;
    private String phone;
    private String userName;
    private String avatar;
    private String token;
}
