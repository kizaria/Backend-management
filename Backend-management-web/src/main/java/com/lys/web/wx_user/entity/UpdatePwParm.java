package com.lys.web.wx_user.entity;


import lombok.Data;

/**
 * 前台修改密码，接收所需实体类
 */
@Data
public class UpdatePwParm {
    private Long userId;
    private String password;
    private String oldPassword;
}
