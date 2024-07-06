package com.lys.web.goods_help.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName goods_help
 */
@TableName(value ="goods_help")
@Data
public class GoodsHelp implements Serializable {
    /**
     * 求购表id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String img;

    /**
     * 审核 0：未通过 1通过
     */
    private String status;

    /**
     * 发布用户
     */
    private Long userId;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;

    /**
     * 是否解决
     */
    private String solved;

    /**
     * 用户昵称 nickName数据库并不存在
     */
    @TableField(exist = false)
    private String nickName;

    /**
     * 用户头像 picture数据库并不存在
     */
    @TableField(exist = false)
    private String picture;

}