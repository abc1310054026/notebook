package com.ce.notebook.service;

import com.ce.notebook.domain.SysUserRepository;
import com.ce.notebook.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限管理
 *
 * @author: ce
 * @create: 2018-12-20 20:01
 **/
@Service
public class SecurityService {

    @Autowired
    private SysUserRepository sysUserRepository;

    /*
     * 验证用户
     * @author ce
     * @date 18-12-20 下午8:04
     * @param [sysUser]
     * @return com.ce.notebook.entity.SysUser
    */
    public SysUser doAuthentication (SysUser sysUser) throws Exception {
        List<SysUser> users = sysUserRepository.findByUsername(sysUser.getUsername());
        if (users.size() == 0)
            throw new Exception("No found user(根据用户名未找到用户)");
        if (users.size() != 1)
            throw new Exception("More then one user be found(有多个用户被查询到)");
        return users.get(0);
    }
}
