package com.lys.web.sys_log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.entity.MyGoodsParm;
import com.lys.web.sys_log.entity.LogParm;
import com.lys.web.sys_log.entity.SysLog;
import com.lys.web.sys_log.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private SysLogService logService;


    /**
     * 删除系统日志， 需要登录，需要log
     * @param logId
     * @return
     */
    //删除日志
    @DeleteMapping("/{logId}")
    @Auth
    @HoneyLogs(operation = "日志",type = LogType.DELETE)
    public ResultVo delete(@PathVariable Long logId){
        if(logService.removeById(logId)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }

    /**
     * 查询系统日志， 需要登录，无需log
     * @param
     * @return
     */
    //查询日志
    @GetMapping("/getLog")
    @Auth
    public ResultVo getLog(LogParm parm){
        Page<SysLog> list =logService.getLog(parm);
        return ResultUtils.success("查询成功",list);
    }
}
