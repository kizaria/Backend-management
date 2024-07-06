package com.lys.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lys.exception.BusinessException;
import com.lys.status.StatusCode;
import com.lys.web.sys_user.entity.SysUser;
import com.lys.web.sys_user.service.SysUserService;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {
    //发布者
    private String issuer;
    //秘钥
    private String secret;
    //过期时间
    private int expiration;
    /**
     * 设置一些参数
     */

    public String generateToken(Map<String,String> map,String userId){
        //设置令牌过期时间
        Calendar instance = Calendar.getInstance();
        //设置失效时间
        instance.add(Calendar.MINUTE,expiration);
        //创建jwt
        JWTCreator.Builder builder = JWT.create();
        // 自定义存储的数据
        map.forEach(builder::withClaim);

        String token=builder.withIssuer(issuer)// issuer 签发者
                .withAudience(userId)
                .withIssuedAt(new Date())
                .withExpiresAt(instance.getTime())// token过期时间 30分钟
                .sign(Algorithm.HMAC256(secret));// token加签加密
        return token;

    }

    /**
     * 验证令牌是否合法
     */
    public boolean verify(String token){
        try{
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        }catch (JWTVerificationException e){
            return false;
        }
        return true;
    }


    /**
     * 校验token，捕获异常并返回错误信息
     * @param token
     * @return
     */
    public DecodedJWT checkToken(String token){
        try {
            return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        }catch (SignatureVerificationException e){
            throw new BusinessException(StatusCode.NO_LOGIN,"无效签名");
        }catch (TokenExpiredException e){
            throw new BusinessException(StatusCode.NO_LOGIN,"token过期");
        }catch (AlgorithmMismatchException e){
            throw new BusinessException(StatusCode.NO_LOGIN,"token算法不一致");
        }catch (Exception e){
            throw new BusinessException(StatusCode.NO_LOGIN,"token无效");
        }
    }

    /**
     * 获取当前登录的用户信息
     */
    public static String getCurrentUser(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token=request.getHeader("token");
            //判空
            if(StringUtils.isNotBlank(token)){
                //解析token
                String userId = JWT.decode(token).getAudience().get(0);
                return userId;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
