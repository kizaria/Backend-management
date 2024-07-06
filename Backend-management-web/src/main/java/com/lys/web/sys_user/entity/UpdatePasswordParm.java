package com.lys.web.sys_user.entity;


import lombok.Data;

/**
 * 后台管理员修改密码，接收实体类
 */
@Data
public class UpdatePasswordParm {
    private Long userId;
    private String password;
    private String oldPassword;
}
