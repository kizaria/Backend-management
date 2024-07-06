package com.lys.web.friend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName friend
 */
@TableName(value ="friend")
@Data
public class Friend implements Serializable {
    /**
     * 好友表id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 当前用户id
     */
    private Integer userid;

    /**
     * 当前用户姓名
     */
    private String username;

    /**
     * 当前用户头像
     */
    private String useravatar;

    /**
     * 好友id
     */
    private Integer friendid;

    /**
     * 好友姓名
     */
    private String friendname;

    /**
     * 好友头像
     */
    private String friendavatar;

    /**
     * 未定
     */
    private String status;


}