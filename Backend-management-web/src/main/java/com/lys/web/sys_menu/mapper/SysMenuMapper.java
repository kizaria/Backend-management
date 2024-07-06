package com.lys.web.sys_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lys.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2024-01-15 19:50:42
* @Entity generator.domain.SysMenu
*/

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //根据id查询菜单
    List<SysMenu> getMenuByUserId(@Param("userId") Long userId);

}




