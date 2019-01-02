package com.ce.notebook.controller;

import com.ce.notebook.entity.SysUser;
import com.ce.notebook.service.SecurityService;
import com.ce.notebook.utils.SecurityUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.netty.handler.codec.base64.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录,权限控制API
 *
 * @author: ce
 * @create: 2018-12-20 20:11
 **/
@RestController
@RequestMapping(value = "/security", produces = "application/json;charset=UTF-8")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<Map> loginIn (@RequestBody SysUser loginUser) throws ServletException {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> resultMap = new HashMap<>();
        try {
            loginUser = securityService.doAuthentication(loginUser);

            String token = SecurityUtils.JWTGenerate(loginUser);
            resultMap.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
            List<String> values = new ArrayList<>();
            values.add("登录失败, 用户名或密码不正确");
            headers.put("message", values);
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(resultMap, headers, HttpStatus.OK);
    }

}
