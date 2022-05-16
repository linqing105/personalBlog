package com.zw.myproject.service;

import com.zw.myproject.pojo.User;

public interface UserService {
    User checkUser(String username, String password);

}
