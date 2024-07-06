package com.lys.web.sys_log.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lys.web.sys_log.entity.LogParm;
import com.lys.web.sys_log.entity.SysLog;
import org.apache.ibatis.annotations.Param;

/**
* @author kiza
* @description 针对表【sys_log】的数据库操作Service
* @createDate 2024-01-30 17:36:27
*/
public interface SysLogService extends IService<SysLog> {
    Page<SysLog> getLog(LogParm parm);
}
