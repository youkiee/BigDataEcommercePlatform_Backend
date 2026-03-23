package com.bigdata.ecommerce.user.service;

import com.bigdata.ecommerce.user.entity.UserBehavior;

import java.util.List;

public interface UserBehaviorService {
    void collectBehavior(UserBehavior behavior);
    List<UserBehavior> getUserBehaviors(Long userId);
}
