package com.gg.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JWTUtil {

    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;
//cant be too short like "abc"
    private static final String TOKEN_SECRET = "abcdegf";


    public static Claims parseJWT(String token) {
        if (StringUtils.isEmpty(token)) return null;
        try {
            Claims claims = Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
            return claims;
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static String createJWT(String userId, String username) {

        String token = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256")
                // 可以将基本不重要的对象信息放到claims
                .claim("username", username)
                .claim("userId", userId)
                .setSubject(username)           // 代表这个JWT的主体，即它的所有人
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))  // 是一个时间戳，代表这个JWT的过期时间；
                .setNotBefore(new Date())// 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
                .compact();

        return token;
    }

    public static String createJWT( String username) {

        String token = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256")
                // 可以将基本不重要的对象信息放到claims
                .claim("username", username)
                .setSubject(username)           // 代表这个JWT的主体，即它的所有人
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))  // 是一个时间戳，代表这个JWT的过期时间；
                .setNotBefore(new Date())// 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
                .compact();

        return token;
    }
    public static String getUsername(String token) {
        return parseJWT(token).getSubject();
    }


    public static String getUserId(String token) {
        return parseJWT(token).get("userId", String.class);
    }


    public static boolean isExpiration(String token) {
        return parseJWT(token).getExpiration().before(new Date());
    }
}
