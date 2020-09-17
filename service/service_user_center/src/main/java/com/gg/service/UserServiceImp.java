package com.gg.service;

import com.gg.beans.User;
import com.gg.mapper.UserMapper;
import com.gg.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public Map login(User user) {
        Map<String, Object> map = new HashMap<>();

        Boolean success=true;
        String phone = user.getPhone();
        String password = user.getPassword();
        if (StringUtils.isEmpty(phone)||StringUtils.isEmpty(password)) success= false;
//        password= Md5Util.getMD5(password);
        User resUser = userMapper.getByPhoneAndPassword(phone, password);
        if(resUser==null||"1".equals(resUser.getDisabled())) success= false;
        else{
            String jwt = JWTUtil.createJWT(String.valueOf(resUser.getId()), resUser.getUsername());
            map.put("token",jwt);
        }
        map.put("success",success);
        return map;
    }

    @Override
    public User register(User user) {
        Integer count = userMapper.countByPhone(user.getPhone());
        if (count==0) {

            User user1 = userMapper.save(user);
            return user1;
        }
        return null;
    }

    @Override
    public User getById(String userId) {
        return userMapper.findById(Integer.valueOf(userId)).get();
    }

    @Override
    public List<User> getUsersByIds(List ids) {
        List<User> userList = userMapper.getUsersByIds(ids);
        return userList;
    }

    @Override
    public Integer getRegisterCountByDate(String date) {

        return  userMapper.getRegisterCountByDate(date);
    }

}
