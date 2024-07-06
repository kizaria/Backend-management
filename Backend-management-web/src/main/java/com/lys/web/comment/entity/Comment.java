package com.lys.web.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * 评论表id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 求购表id
     */
    private Long foreignId;

    /**
     * 父级评论id
     */
    private Long pid;

    /**
     * 回复对象
     */
    private String target;

    /**
     * 创建时间
     */
    private Date creatime;

    /**
     * 子集
     */
    @TableField(exist = false)
    private List<Comment> children;

}