//package com.gg.service;
//
//import com.gg.beans.BackUser;
//import com.gg.beans.CurrentUser;
//import com.gg.beans.SecurityUser;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class UserDetailsServiceImp implements UserDetailsService {
//
//    @Autowired
//    PermissionService permissionService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 从数据库中取出用户信息
////        BackUser backUser = backUserService.getBackUserByUserName(username);
////        System.out.println(backUser);
////        // 判断用户是否存在
////        if (null == backUser){
////            throw new RuntimeException("用户名不存在！");
////        }
////        // 返回UserDetails实现类
////        CurrentUser currentUser = new CurrentUser();
////        BeanUtils.copyProperties(backUser,currentUser);
//
////        List<String> authorities = permissionService.getPermissionNameByUserId(backUser.getId());
//        List<String> authorities= Arrays.asList("a","b");
//        SecurityUser securityUser = new SecurityUser();
////        securityUser.setCurrentUser(currentUser);
//        securityUser.setPermissionList(authorities);
//        return securityUser;
//    }
//
//}
