package com.lys.web.user_menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user_menu
 */
@TableName(value ="user_menu")
@Data
public class UserMenu{
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer userMenuId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 菜单id
     */
    private Integer menuId;

}