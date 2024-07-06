package com.lys.web.sys_user.entity;


import lombok.Data;

/**
 * 后台登录接收的实体类
 */
@Data
public class LoginParm {
    private String username;
    private String password;
    private String code;
}
