package com.lys.web.user_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lys.web.user_menu.entity.AssignParm;
import com.lys.web.user_menu.entity.UserMenu;

/**
* @author kiza
* @description 针对表【user_menu】的数据库操作Service
* @createDate 2024-01-19 17:58:32
*/
public interface UserMenuService extends IService<UserMenu> {

    void saveMenu(AssignParm parm);
}
