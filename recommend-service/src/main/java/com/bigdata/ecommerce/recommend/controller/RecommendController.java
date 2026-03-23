package com.bigdata.ecommerce.recommend.controller;

import com.bigdata.ecommerce.recommend.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserRecommendations(@PathVariable Long userId, 
                                                  @RequestParam(defaultValue = "10") int limit) {
        List<Long> recommendations = recommendService.getUserRecommendations(userId, limit);
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/feedback")
    public ResponseEntity<?> submitFeedback(@RequestBody FeedbackRequest feedback) {
        recommendService.submitFeedback(feedback.getUserId(), feedback.getItemId(), 
                                      feedback.getAction(), feedback.getScore());
        return ResponseEntity.ok("Feedback submitted successfully");
    }

    @GetMapping("/strategy/list")
    public ResponseEntity<?> listStrategies() {
        List<String> strategies = recommendService.listStrategies();
        return ResponseEntity.ok(strategies);
    }

    @PostMapping("/strategy/set")
    public ResponseEntity<?> setStrategy(@RequestBody StrategyRequest strategyRequest) {
        recommendService.setStrategy(strategyRequest.getUserId(), strategyRequest.getStrategy());
        return ResponseEntity.ok("Strategy set successfully");
    }

    public static class FeedbackRequest {
        private Long userId;
        private Long itemId;
        private String action;
        private Integer score;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }
    }

    public static class StrategyRequest {
        private Long userId;
        private String strategy;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }
    }
}
