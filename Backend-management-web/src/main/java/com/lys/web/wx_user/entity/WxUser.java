package com.lys.web.wx_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName wx_user
 */
@TableName(value ="wx_user")
@Data
public class WxUser {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String picture;

    /**
     * 电话
     */
    private String phone;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态 0是启用 1是禁用
     */
    private String status;

    /**
     * 验证码
     */
    @TableField(exist = false)
    private String code;

    /**
     * 建号时间
     */
    private Date time;

}