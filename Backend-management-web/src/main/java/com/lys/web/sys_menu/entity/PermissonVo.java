package com.lys.web.sys_menu.entity;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermissonVo {
    //当前用户的菜单
    List<SysMenu> menuList =new ArrayList<>();
    //回显的数据，被分配的用户的菜单
    private Object[] checkList;
}
