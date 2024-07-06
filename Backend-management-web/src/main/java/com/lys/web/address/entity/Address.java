package com.lys.web.address.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName address
 */
@TableName(value ="address")
@Data
public class Address implements Serializable {
    /**
     * 地址表id
     */
    @TableId(type = IdType.AUTO)
    private Integer addressid;

    /**
     * 用户id
     */
    private Integer userid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址详情
     */
    private String addressdesc;

    /**
     * 默认地址 0：是 1：否
     */
    private String isdefault;

}