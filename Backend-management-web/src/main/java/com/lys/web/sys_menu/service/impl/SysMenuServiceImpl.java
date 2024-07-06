package com.lys.web.sys_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.sys_menu.entity.MakeMenuTree;
import com.lys.web.sys_menu.entity.SysMenu;
import com.lys.web.sys_menu.mapper.SysMenuMapper;
import com.lys.web.sys_menu.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author kiza
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2024-01-15 19:50:42
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService {



    @Override
    public List<SysMenu> getParent() {
        //查询出所有的菜单
        LambdaQueryWrapper<SysMenu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getType,"1")
                .orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menuList =this.baseMapper.selectList(queryWrapper);

        //构造顶级菜单
        SysMenu menu=new SysMenu();
        menu.setMenuId(0L);
        menu.setParentId(-1L)    ;
        menu.setTitle("顶级菜单");
        menu.setLabel("顶级菜单");
        menu.setValue(0L);
        //添加到菜单数据
        menuList.add(menu);
        //组装树形数据
        return MakeMenuTree.makeTree(menuList,-1L);
    }

    @Override
    public List<SysMenu> getMenuByUserId(Long userId) {
        return this.baseMapper.getMenuByUserId(userId);
    }
}




