package com.zw.myproject.service.serviceImpl;

import com.zw.myproject.dao.UserRepository;
import com.zw.myproject.pojo.User;
import com.zw.myproject.service.UserService;
import com.zw.myproject.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
