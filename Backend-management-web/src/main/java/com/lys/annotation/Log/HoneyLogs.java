package com.lys.annotation.Log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HoneyLogs {
    // 操作的模块
    String operation();
    // 操作类型
    LogType type();
}
