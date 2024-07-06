package com.lys.web.sys_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lys.web.sys_menu.entity.SysMenu;

import java.util.List;

/**
* @author kiza
* @description 针对表【sys_menu】的数据库操作Service
* @createDate 2024-01-15 19:50:42
*/
public interface SysMenuService extends IService<SysMenu> {
    //获取上级菜
    public List<SysMenu> getParent();
    //根据用户id查询菜单
    List<SysMenu> getMenuByUserId(Long userId);
}
