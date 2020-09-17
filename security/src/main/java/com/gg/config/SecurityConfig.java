//package com.gg.config;
//
//import com.gg.beans.TokenLogoutHandler;
//import com.gg.beans.UnauthorizedEntryPoint;
//import com.gg.filter.TokenAuthenticationFilter;
//import com.gg.filter.TokenLoginFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
////@Configuration
////@EnableWebSecurity
////@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//
//    private UserDetailsService userDetailsService;
//@Autowired
//    private RedisTemplate redisTemplate;
//
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.exceptionHandling()
//                .authenticationEntryPoint(new UnauthorizedEntryPoint())
//                .and().csrf().disable()
//                .authorizeRequests().anyRequest().authenticated().and()
//                .logout().logoutUrl("/logout")
//                .addLogoutHandler(new TokenLogoutHandler(redisTemplate))
//                .and().addFilter(new TokenLoginFilter(authenticationManager(),redisTemplate))
//                .addFilter(new TokenAuthenticationFilter(authenticationManager(),redisTemplate))
//                .httpBasic();
//    }
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/swagger-ui/**","/health","/hystrix.stream");
//    }
//}
