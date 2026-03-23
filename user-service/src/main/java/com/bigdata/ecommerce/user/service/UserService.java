package com.bigdata.ecommerce.user.service;

import com.bigdata.ecommerce.user.entity.User;

public interface UserService {
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
}
