package com.lys.web.sys_log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.lys.annotation.Log.HoneyLogs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName sys_log
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value ="sys_log")
@Data
public class SysLog implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long logId;

    /**
     * 操作名称
     */
    private String operation;

    /**
     * 操作类型
     */
    private String type;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 操作人
     */
    private String logUser;

    /**
     * 操作时间
     */
    private Date time;

    /**
     * 操作人的名字
     */
    @TableField(exist = false)
    private String username;

}