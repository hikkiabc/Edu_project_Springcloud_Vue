package com.gg.service;

import com.gg.beans.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map login(User user);

    User register(User user);

    User getById(String userId);

    List<User> getUsersByIds(List ids);

    Integer getRegisterCountByDate(String date);
}
