package com.bigdata.ecommerce.recommend.service;

import java.util.List;

public interface RecommendService {
    List<Long> getUserRecommendations(Long userId, int limit);
    void submitFeedback(Long userId, Long itemId, String action, Integer score);
    List<String> listStrategies();
    void setStrategy(Long userId, String strategy);
}
