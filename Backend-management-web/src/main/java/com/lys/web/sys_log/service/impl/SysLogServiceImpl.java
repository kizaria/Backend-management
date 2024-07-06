package com.lys.web.sys_log.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.goods.entity.Goods;
import com.lys.web.sys_log.entity.LogParm;
import com.lys.web.sys_log.entity.SysLog;
import com.lys.web.sys_log.mapper.SysLogMapper;
import com.lys.web.sys_log.service.SysLogService;
import org.springframework.stereotype.Service;

/**
* @author kiza
* @description 针对表【sys_log】的数据库操作Service实现
* @createDate 2024-01-30 17:36:27
*/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
    implements SysLogService {

    @Override
    public Page<SysLog> getLog(LogParm parm) {
        Page<SysLog> page=new Page<>(parm.getCurrentPage(),parm.getPageSize());
        return this.baseMapper.getLog(page,parm.getUsername());
    }
}




