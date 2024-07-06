package com.lys.web.sys_user.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理员登录成功返回的数据
 */
@Data
public class LoginVo {
    private Long userId;
    private String nickName;
    private String token;
    private List<MenuVo> menuList=new ArrayList<>();
    private List<String> codeList=new ArrayList<>();
}
