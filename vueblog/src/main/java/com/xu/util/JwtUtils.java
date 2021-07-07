package com.xu.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * jwt工具类 生成和校验jwt
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "vueblog.jwt")
public class JwtUtils {
    
    private String secret;  //f4e2e52034348f86b67cde581c0f9eb5
    private long expire;  //604800
    private String header;  //Authorization

    /**
     * 生成jwt token
     */
    public String generateToken(long userId) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        
        return Jwts.builder() //new 一个JwtBuilder,设置jwt的body
                .setHeaderParam("typ", "JWT") //设置jwt的header参数 
                .setSubject(userId + "")  //代表jwt的主体，即它的所有人，是一个json格式的字符串，作为用户的唯一标识
                .setIssuedAt(nowDate)  //jwt的签发时间
                .setExpiration(expireDate)   //设置过期时间
                .signWith(SignatureAlgorithm.HS512, secret)   //设置签名使用的算法和密钥
                .compact(); //压缩为xxxx.xxxx.xxx格式的jwt
    }

    /**
     * 获取jwt的信息
     * @param token
     * @return
     */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()             //得到DefaultJwtParser
                    .setSigningKey(secret)  //设置签名的secret
                    .parseClaimsJws(token) //需要解析的jwt
                    .getBody();
        }catch (Exception e) {
            log.debug("validate is token error", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return true: 过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
