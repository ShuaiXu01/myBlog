package com.xu.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Shiro默认supports是 UsernamePasswordToken (extends AuthenticationToken)
 * 采用jwt的方式则要自定义JwtToken，完成Shiro的supports方法
 */
public class JwtToken implements AuthenticationToken {
    
    private String token;
    
    public JwtToken(String jwt) {
        this.token = jwt;
    }

    /**
     * 收集用户提交的身份，如：用户名
     * @return
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 收集用户提交的凭据，如：密码
     * @return
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
