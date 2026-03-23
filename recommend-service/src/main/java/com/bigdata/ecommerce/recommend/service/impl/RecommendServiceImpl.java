package com.bigdata.ecommerce.recommend.service.impl;

import com.bigdata.ecommerce.recommend.service.RecommendService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private List<String> strategies = Arrays.asList("collaborative_filtering", "content_based", "hybrid");
    private Map<Long, String> userStrategies = new HashMap<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Long> getUserRecommendations(Long userId, int limit) {
        // 先从缓存获取
        String cacheKey = "recommend:user:" + userId;
        String cachedRecommendations = redisTemplate.opsForValue().get(cacheKey);
        if (cachedRecommendations != null) {
            try {
                List<Long> recommendations = objectMapper.readValue(cachedRecommendations, List.class);
                return recommendations.subList(0, Math.min(limit, recommendations.size()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        // 生成推荐结果（模拟）
        List<Long> recommendations = generateRecommendations(userId, limit);

        // 缓存推荐结果
        try {
            String recommendationsJson = objectMapper.writeValueAsString(recommendations);
            redisTemplate.opsForValue().set(cacheKey, recommendationsJson, 1, TimeUnit.HOURS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return recommendations;
    }

    @Override
    public void submitFeedback(Long userId, Long itemId, String action, Integer score) {
        // 处理用户反馈
        Map<String, Object> feedback = new HashMap<>();
        feedback.put("userId", userId);
        feedback.put("itemId", itemId);
        feedback.put("action", action);
        feedback.put("score", score);

        // 发送到Kafka
        try {
            String feedbackJson = objectMapper.writeValueAsString(feedback);
            kafkaTemplate.send("recommend-event-topic", feedbackJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 清除缓存，下次重新计算推荐
        String cacheKey = "recommend:user:" + userId;
        redisTemplate.delete(cacheKey);
    }

    @Override
    public List<String> listStrategies() {
        return strategies;
    }

    @Override
    public void setStrategy(Long userId, String strategy) {
        if (strategies.contains(strategy)) {
            userStrategies.put(userId, strategy);
            // 清除缓存，下次使用新策略
            String cacheKey = "recommend:user:" + userId;
            redisTemplate.delete(cacheKey);
        }
    }

    private List<Long> generateRecommendations(Long userId, int limit) {
        // 模拟推荐算法
        List<Long> recommendations = new ArrayList<>();
        for (long i = 1; i <= limit; i++) {
            recommendations.add(i);
        }
        return recommendations;
    }
}
