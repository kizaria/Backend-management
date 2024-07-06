package com.lys.annotation.Auth;

import com.lys.exception.BusinessException;
import com.lys.jwt.JwtUtils;
import com.lys.status.StatusCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Auth annotation = null;

        //判断当前拦截到的是controller的方法还是其他资源
        if (handler instanceof HandlerMethod) {
            //如果方法被Auth注解标记 此元素上存在指定的注释类型，则此方法返回该方法的注释，否则返回null。
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);
        }
        //没有被@Auth注解标识，不做token验证,直接放行
        if (annotation == null) {
            return true;
        }
        //获取token 反复验证
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }

        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(StatusCode.NO_LOGIN, "token不能为空");
        }
        //验证token
        jwtUtils.checkToken(token);

        return true;
    }
}
