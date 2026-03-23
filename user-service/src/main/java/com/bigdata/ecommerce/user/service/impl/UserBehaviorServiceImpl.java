package com.bigdata.ecommerce.user.service.impl;

import com.bigdata.ecommerce.user.entity.UserBehavior;
import com.bigdata.ecommerce.user.service.UserBehaviorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBehaviorServiceImpl implements UserBehaviorService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Map<Long, List<UserBehavior>> userBehaviorMap = new HashMap<>();
    private Long nextId = 1L;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void collectBehavior(UserBehavior behavior) {
        behavior.setId(nextId++);
        behavior.setCreateTime(new Date());

        // 保存到内存（实际项目中应该保存到数据库）
        userBehaviorMap.computeIfAbsent(behavior.getUserId(), k -> new ArrayList<>()).add(behavior);

        // 发送到Kafka
        try {
            String behaviorJson = objectMapper.writeValueAsString(behavior);
            kafkaTemplate.send("user-behavior-topic", behaviorJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UserBehavior> getUserBehaviors(Long userId) {
        return userBehaviorMap.getOrDefault(userId, new ArrayList<>());
    }
}
