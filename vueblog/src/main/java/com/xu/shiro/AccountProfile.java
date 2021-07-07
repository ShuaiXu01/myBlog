package com.xu.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录成功后返回的用户信息的载体，不包含敏感信息，如：password
 */

@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;
    
}
