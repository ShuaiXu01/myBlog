package com.xu.shiro;

import com.xu.entity.User;
import com.xu.service.UserService;
import com.xu.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AccountRealm extends AuthorizingRealm {
    
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    /**
     * 让realm支持jwt的凭证校验
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 权限校验
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录认证校验
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        log.info("jwtToken -> " + jwtToken);
        //获取用户信息
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userService.getById(Long.valueOf(userId));
        log.info("user -> " + user);
        
        //判断用户状态
        if (user == null) {
            throw new UnknownAccountException("账户不存在"); 
        }
        if (user.getStatus() == -1) {
            throw new LockedAccountException("账户已被锁定");
        }
        
        AccountProfile profile = new AccountProfile();
        BeanUtils.copyProperties(user, profile);
        log.info("profile -> " + profile);
            
        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}
