package com.zsc.salary.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * token 工具类 生成 和验证token
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/7/13 11:03
 */
@Component
public class TokenUtil {

    @Resource
    private AesEncryptUtil aesEncryptUtil;

    /**
     * 过期时间 2天
     */
    private static final long EXPIRE_DATE= 60 * 60 * 24 * 2;

    /**
     * token密匙
     */
    private static final String TOKEN_SECRET = "dDanDang2020";

    public String createToken (Map<String, Object> map) {
        //设置失效时间
        long nowTime = System.currentTimeMillis();
        long time = nowTime+EXPIRE_DATE;

        //创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setId(map.get("id").toString())
                .setSubject("JSON Web Token")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                //过期时间
                .setExpiration(new Date(time))
                .setIssuer("DDanDang");

        //根据Map设置claims
        for(Map.Entry<String, Object> entry: map.entrySet()){
            jwtBuilder.claim(entry.getKey(),entry.getValue());
        }

        //创建token
        //给token加密
        return aesEncryptUtil.encrypt(jwtBuilder.compact());
    }

    /**
     * 解析token 获取claims
     * @param token token
     * @return claim 可以获取到里面的数据 get
     */
    public Claims parseJwt(String token){
        //传入双重加密的token 然后进行解密并且解析
        String newToken = aesEncryptUtil.desEncrypt(token);
        return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(newToken).getBody();
    }
}
