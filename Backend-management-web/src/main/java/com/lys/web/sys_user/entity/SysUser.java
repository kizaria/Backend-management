package com.lys.web.sys_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName sys_user
 */
@Data
@TableName(value ="sys_user")
public class SysUser {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 性别

     */
    private String sex;

    /**
     * 状态 0：启用 1：停用
     */
    private String status;

    /**
     * 是否是超级管理员 0：否 1：是
     */
    private String isAdmin;

}