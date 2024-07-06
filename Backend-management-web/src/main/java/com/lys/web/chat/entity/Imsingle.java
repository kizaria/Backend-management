package com.lys.web.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName imsingle
 */
@TableName(value ="imsingle")
@Data
public class Imsingle implements Serializable {
    /**
     * 聊天表id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送人
     */
    private String fromuser;

    /**
     * 发送人头像
     */
    private String fromavater;

    /**
     * 时间
     */
    private Date time;

    /**
     * 接收人
     */
    private String touser;

    /**
     * 接收人头像
     */
    private String toavater;

    /**
     * 0:未读 1:已读
     */
    private String readed;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}