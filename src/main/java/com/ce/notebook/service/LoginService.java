package com.ce.notebook.service;

import com.ce.notebook.domain.SysUserRepository;
import com.ce.notebook.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录验证
 *
 * @author: ce
 * @create: 2018-10-23 16:04
 **/
@Service
public class LoginService extends BaseService {

    @Autowired
    private SysUserRepository sysUserRepository;

    public Boolean CheckUser (SysUser user) {
        /*
        * 调用user的setId方法可以改变被传入对象user的属性值
        * 如果用user = t直接对对象赋值,则传入对象user不发生改变*/
        user.setId(1L);
        user = sysUserRepository.findByUsername(user.getUsername()).get(0);
        if (user.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
