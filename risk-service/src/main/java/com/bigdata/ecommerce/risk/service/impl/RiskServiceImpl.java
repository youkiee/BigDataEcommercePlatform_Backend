package com.bigdata.ecommerce.risk.service.impl;

import com.bigdata.ecommerce.risk.controller.RiskController;
import com.bigdata.ecommerce.risk.service.RiskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RiskServiceImpl implements RiskService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Long nextCaseId = 1L;
    private Map<Long, Map<String, Object>> cases = new HashMap<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> scanRisk(RiskController.RiskRequest riskRequest) {
        Map<String, Object> result = new HashMap<>();
        Long userId = riskRequest.getUserId();
        String action = riskRequest.getAction();

        // 计算风险评分
        double riskScore = calculateRiskScore(userId, action, riskRequest.getContext());

        // 判定风险等级
        String riskLevel = getRiskLevel(riskScore);

        // 生成风控结果
        result.put("userId", userId);
        result.put("action", action);
        result.put("riskScore", riskScore);
        result.put("riskLevel", riskLevel);
        result.put("timestamp", System.currentTimeMillis());

        // 发送风险事件到Kafka
        try {
            String riskEventJson = objectMapper.writeValueAsString(result);
            kafkaTemplate.send("risk-event-topic", riskEventJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 缓存风险评分
        redisTemplate.opsForValue().set("risk:score:" + userId, String.valueOf(riskScore), 24, TimeUnit.HOURS);

        return result;
    }

    @Override
    public double getRiskScore(Long userId) {
        String cachedScore = redisTemplate.opsForValue().get("risk:score:" + userId);
        if (cachedScore != null) {
            return Double.parseDouble(cachedScore);
        }
        // 计算默认风险评分
        return calculateRiskScore(userId, "query", new HashMap<>());
    }

    @Override
    public Long createCase(RiskController.CaseRequest caseRequest) {
        Long caseId = nextCaseId++;
        Map<String, Object> caseInfo = new HashMap<>();
        caseInfo.put("id", caseId);
        caseInfo.put("userId", caseRequest.getUserId());
        caseInfo.put("riskType", caseRequest.getRiskType());
        caseInfo.put("description", caseRequest.getDescription());
        caseInfo.put("evidence", caseRequest.getEvidence());
        caseInfo.put("status", "created");
        caseInfo.put("createTime", System.currentTimeMillis());

        cases.put(caseId, caseInfo);
        return caseId;
    }

    @Override
    public Map<String, Object> getCase(Long caseId) {
        return cases.get(caseId);
    }

    private double calculateRiskScore(Long userId, String action, Map<String, Object> context) {
        // 模拟风险评分计算
        double baseScore = 50.0;
        
        // 根据用户ID生成不同的风险评分
        baseScore += (userId % 10) * 5;
        
        // 根据操作类型调整评分
        if ("login".equals(action)) {
            baseScore += 10;
        } else if ("payment".equals(action)) {
            baseScore += 20;
        }
        
        // 限制评分范围在0-100之间
        return Math.min(100.0, Math.max(0.0, baseScore));
    }

    private String getRiskLevel(double riskScore) {
        if (riskScore >= 80) {
            return "high";
        } else if (riskScore >= 50) {
            return "medium";
        } else {
            return "low";
        }
    }
}
