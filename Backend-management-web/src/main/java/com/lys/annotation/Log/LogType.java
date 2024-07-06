package com.lys.annotation.Log;

/**
 * 系统日志的操作类型枚举
 */
public enum LogType {
    ADD("新增"),
    UPDATE("修改"),
    DELETE("删除"),
    RE("注册"),
    LOGIN("登录"),
    EXPORT("导出数据");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    LogType(String value) {
        this.value = value;
    }
}
