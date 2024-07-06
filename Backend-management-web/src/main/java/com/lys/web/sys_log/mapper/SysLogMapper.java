package com.lys.web.sys_log.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.web.goods.entity.Goods;
import com.lys.web.sys_log.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author kiza
* @description 针对表【sys_log】的数据库操作Mapper
* @createDate 2024-01-30 17:36:27
* @Entity generator.domain.SysLog
*/

@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    //模糊查询
    Page<SysLog> getLog(Page<SysLog> page, @Param("username") String username);
}




