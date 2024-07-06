package com.lys.web.user_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.user_menu.entity.AssignParm;
import com.lys.web.user_menu.entity.UserMenu;
import com.lys.web.user_menu.mapper.UserMenuMapper;
import com.lys.web.user_menu.service.UserMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

/**
* @author kiza
* @description 针对表【user_menu】的数据库操作Service实现
* @createDate 2024-01-19 17:58:31
*/
@Service
public class UserMenuServiceImpl extends ServiceImpl<UserMenuMapper, UserMenu>
    implements UserMenuService {

    @Override
    @Transactional//事务管理
    public void saveMenu(AssignParm parm) {
        //先删除，在插入
        //1.删除
        LambdaQueryWrapper<UserMenu> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(UserMenu::getUserId,parm.getAssId());
        this.baseMapper.delete(queryWrapper);
        //2.删除成功，插入
        this.baseMapper.saveMenu(parm.getAssId(),parm.getList());
    }
}




