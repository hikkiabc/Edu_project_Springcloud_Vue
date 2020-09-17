package com.gg.beans;

import com.gg.utils.JWTUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenLogoutHandler implements LogoutHandler {

    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if (token != null) {
            System.out.println("please remove token from client cookie");

            //清空当前用户缓存中的权限数据
            String userName = JWTUtil.getUsername(token);
            redisTemplate.delete(userName);
        }
        try {
            response.getWriter().write("authentication failed " );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}