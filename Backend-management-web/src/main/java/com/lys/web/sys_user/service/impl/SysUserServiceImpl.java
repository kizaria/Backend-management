package com.lys.web.sys_user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.sys_user.entity.SysUser;
import com.lys.web.sys_user.mapper.SysUserMapper;
import com.lys.web.sys_user.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
