package com.lys.web.sys_menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class SysMenu    {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 权限字段
     */
    private String code;

    /**
     * 图标
     */
    private String icon;

    /**
     * 序号
     */
    private Long orderNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上级菜单名称
     */
    private String parentName;

    /**
     * 1：菜单 2：按钮
     */
    private String type;

    /**
     * 路由名称
     */
    private String path;

    @TableField(exist = false)
    private Long value;

    @TableField(exist = false)
    private String label;

    @TableField(exist = false)
    private List<SysMenu> children =new ArrayList<>();

}