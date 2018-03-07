package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getAll() {
        return userMapper.getAll();
    }

    public User getOne(String uuid) {
        return userMapper.getOne(uuid);
    }

    public String insert(User user) {
        userMapper.insert(user);
        String userId = user.getId();
        return userId;

    }

    public String update(User user) {
        userMapper.update(user);
        String userId = user.getId();
        return userId;

    }

    
}
