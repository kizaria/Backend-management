package com.lys.web.user_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lys.web.user_menu.entity.UserMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【user_menu】的数据库操作Mapper
* @createDate 2024-01-19 17:58:31
* @Entity generator.domain.UserMenu
*/
@Mapper
public interface UserMenuMapper extends BaseMapper<UserMenu> {
    boolean saveMenu(@Param("userId") Long userId, @Param("menuIds") List<Long> menuIds);
}




