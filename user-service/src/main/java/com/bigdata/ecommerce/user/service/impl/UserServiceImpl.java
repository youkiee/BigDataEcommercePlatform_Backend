package com.bigdata.ecommerce.user.service.impl;

import com.bigdata.ecommerce.user.entity.User;
import com.bigdata.ecommerce.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private Map<Long, User> userMap = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public User getUserById(Long id) {
        return userMap.get(id);
    }

    @Override
    public User createUser(User user) {
        user.setId(nextId++);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        user.setUpdateTime(new Date());
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userMap.remove(id);
    }
}
