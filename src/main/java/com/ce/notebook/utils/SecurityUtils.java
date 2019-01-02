package com.ce.notebook.utils;

import com.ce.notebook.entity.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证,权限相关
 *
 * @author: ce
 * @create: 2018-12-31 13:34
 **/
public class SecurityUtils {

    public static SysUser getCurrentUser () {
        SysUser currentUser = new SysUser();
        HttpServletRequest request = getCurrentRequest();
        Claims claims = JWTBody(request.getHeader("authorization"));
        currentUser.setUsername(claims.getSubject());
        return currentUser;
    }

    public static HttpServletRequest getCurrentRequest () {
        return getCurrentRequestAttributes().getRequest();
    }

    public static HttpServletResponse getCurrentResponse () {
        return getCurrentRequestAttributes().getResponse();
    }

    private static ServletRequestAttributes getCurrentRequestAttributes () {
        return (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
    }

    public static Boolean JWTValidate (String token) {
        return JWTValidate(token, "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
    }

    public static Boolean JWTValidate (String token, String secret) {
        Claims claims;
//        校验JWT正确性
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (SignatureException se) {
            se.printStackTrace();
            return false;
        }
        return true;
    }

    public static String JWTGenerate (SysUser sysUser) {
        JwtBuilder builder = Jwts.builder();
//        向jwt设置用户名
        builder.setSubject(sysUser.getUsername());
        builder.signWith(Keys.hmacShaKeyFor("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq".getBytes()));
        String token = builder.compact();
        return token;
    }

    public static Claims JWTBody (String token) {
        return JWTBody(token, "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
    }

    public static Claims JWTBody (String token, String secret) {
        token = token.substring(7);
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }
}
