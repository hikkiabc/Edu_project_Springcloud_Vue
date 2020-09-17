package com.gg.controller;


import com.gg.beans.User;
import com.gg.mapper.UserMapper;
import com.gg.service.UserService;
import com.gg.utils.CommonResult;
import com.gg.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;
@Autowired
    UserMapper userMapper;
    @PostMapping("/login")
    public CommonResult login(@RequestBody User user) {

        CommonResult commonResult = new CommonResult();
        Map res = userService.login(user);
        if ((Boolean) res.get("success")) {
            Object token = res.get("token");
            commonResult.setData(token);
        } else commonResult.setSuccess(false);
        return commonResult;

    }

    @PostMapping("/register")
    public CommonResult register(@RequestBody User user) {
        CommonResult commonResult = new CommonResult();
        String password = user.getPassword();
        String username = user.getUsername();
        String phone = user.getPhone();
        String code = user.getCode();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            commonResult.setData("empty");
            return commonResult;
        }
        String redisCode = String.valueOf(redisTemplate.opsForValue().get(phone));
        if (!code.equals(redisCode)) {
            commonResult.setData("wrong code");
            return commonResult;
        }
//        user.setPassword(Md5Util.getMD5(password));
        User user1 = userService.register(user);
        if (user1 == null) {
            commonResult.setData("exist phone");
            return commonResult;
        }
        commonResult.setData(user1);
        return commonResult;
    }

    @GetMapping("/login")
    public CommonResult getUserByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = JWTUtil.getUserId(token);
        User user = userService.getById(userId);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(user);
        return commonResult;
    }
    @GetMapping("/login/{id}")
    public CommonResult getUserByUserId(@PathVariable String id) {

        User user = userService.getById(id);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(user);
        return commonResult;
    }
    //String url=String.format(baseUrl,"a","b")
    //String baseUrl=www.a.com/?a=%s&b=%s
    @PostMapping
    public List getUsersByIds(@RequestBody Set<Integer> ids) {
        List list = new ArrayList<>(ids);
        List<User> userList =  userService.getUsersByIds(list);
        return userList;
    }
//    @GetMapping("/test")
//    public void add(){
//      userMapper.save(new User());
//    }
    @GetMapping("/logincountbydate")
    public Integer getRegisterCountByDate(String date){
      return userService.getRegisterCountByDate(date);
    }
}
