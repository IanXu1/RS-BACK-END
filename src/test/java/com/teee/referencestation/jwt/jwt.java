package com.teee.referencestation.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.teee.referencestation.common.exception.CustomException;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

public class jwt {

    public static void main(String[] args) {
        String token = sign("123", 123, String.valueOf(Instant.now().toEpochMilli()));
        System.out.println(getClaim(token, "account"));
        System.out.println(getClaim(token, "userId"));
        System.out.println(getClaim(token, "currentTimeMillis"));
    }


    /**
     * 生成签名
     *
     * @param account 帐号
     * @return java.lang.String 返回加密的Token
     */
    public static String sign(String account, long userId, String currentTimeMillis) {
        try {
            // 帐号加JWT私钥加密
            String secret = account + new String(Base64.getDecoder().decode("U0JBUElKV1RkV2FuZzkyNjQ1NA=="), "utf-8");
            // 此处过期时间
            LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(300);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim("account", account)
                    .withClaim("userId", userId + "")
                    .withClaim("currentTimeMillis", currentTimeMillis)
                    .withExpiresAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new CustomException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }


    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            throw new CustomException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }
}
