package com.gg.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg.beans.CurrentUser;
import com.gg.beans.SecurityUser;
import com.gg.utils.CommonResult;
import com.gg.utils.JWTUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager,  RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            CurrentUser currentUser = new ObjectMapper().readValue(req.getInputStream(), CurrentUser.class);
            System.out.println(currentUser.getUserName());
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(currentUser.getUserName(), currentUser.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录成功
     * @param req
     * @param res
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        String token = JWTUtil.createJWT(securityUser.getCurrentUser().getUserName());
        redisTemplate.opsForValue().set(securityUser.getCurrentUser().getUserName(), securityUser.getPermissionList());
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json; charset=utf-8");
        res.setStatus(HttpStatus.OK.value());
        res.setHeader("token",token);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(token);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(res.getWriter(), commonResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 登录失败
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write("authentication failed " );

    }
}