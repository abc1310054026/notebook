package com.ce.notebook.configuration.shiro.realm;

import com.ce.notebook.configuration.shiro.ShiroConfig;
import com.ce.notebook.entity.SysUser;
import com.ce.notebook.service.LoginService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户名密码验证
 *
 * @author: ce
 * @create: 2018-10-23 15:12
 **/
public class UsernamePasswordRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    static Logger logger = Logger.getLogger(ShiroConfig.class.getName());

    @Override
    public String getName() {
        return "UsernamePasswordRealm";
    }

    /*
     * 判断token类型是否支持
     * @author ce
     * @date 18-10-23 下午3:33
     * @param [token]
     * @return boolean
    */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info(getName() + ": " + "获取授权信息");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (principalCollection.getRealmNames().contains(getName())) {
            simpleAuthorizationInfo.addRole("user");
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info(getName() + ": " + "获取认证信息");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        SysUser user = new SysUser(username, password);
        if (loginService.CheckUser(user)) {
            return new SimpleAuthenticationInfo(user.getId(), password, getName());
        } else {
            throw new IncorrectCredentialsException("密码错误");
        }
    }
}
