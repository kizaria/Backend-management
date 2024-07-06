package com.lys.aop;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.jwt.JwtUtils;
import com.lys.utils.IpUtils;
import com.lys.web.sys_log.entity.SysLog;
import com.lys.web.sys_log.service.SysLogService;
import com.lys.web.sys_user.entity.LoginParm;
import com.lys.web.sys_user.entity.SysUser;
import com.lys.web.sys_user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.reflection.ArrayUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 记录日志
 */
@Component
@Aspect
@Slf4j
public class LogsAspect {

    @Autowired
    private  SysLogService logsService;

    @Autowired
    private SysUserService sysUserService;

    //在所有被honeyLogs注解的方法后操作
    @AfterReturning(pointcut = "@annotation(honeyLogs)", returning = "jsonResult")
    public void recordLog(JoinPoint joinPoint, HoneyLogs honeyLogs, Object jsonResult) {
        // 获取当前登录的用户的信息
        String userId = JwtUtils.getCurrentUser();
        SysUser loginUser=null;
        loginUser=sysUserService.getById(userId);

        if (loginUser == null) { // 用户未登录的情况下  loginUser是null  是null的话我们就要从参数里面获取操作人信息
            // 登录、注册
            Object[] args = joinPoint.getArgs();
            if (ArrayUtils.isEmpty(args)) {
                if (args[0] instanceof LoginParm) {
                    LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
                    queryWrapper.eq(SysUser::getUsername,((LoginParm) args[0]).getUsername())
                            .eq(SysUser::getPassword, DigestUtils.md5DigestAsHex(((LoginParm) args[0]).getPassword().getBytes()));

                    loginUser =sysUserService.getOne(queryWrapper);
                }
            }
        }
        if (loginUser == null) {
            log.error("记录日志信息报错，未获取到当前操作用户信息");
            return;
        }
        //获取HttpServletRequest对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        // 获取到请求的ip
        String ip = IpUtils.getIpAddr(request);
        SysLog logs = SysLog.builder()
                .operation(honeyLogs.operation())
                .type(honeyLogs.type().getValue())
                .ip(ip)
                .logUser(String.valueOf(loginUser.getUserId()))
                .time(new Date())
                .build();

        ThreadUtil.execAsync(() -> {
            // 异步记录日志信息
            //不影响其他代码
            logsService.save(logs);
        });
    }

}