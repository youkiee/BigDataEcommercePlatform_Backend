package com.bigdata.ecommerce.system.service.impl;

import com.bigdata.ecommerce.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Long nextTaskId = 1L;
    private Map<Long, Map<String, Object>> tasks = new HashMap<>();

    @Override
    public String getConfig(String key) {
        return redisTemplate.opsForValue().get("system:config:" + key);
    }

    @Override
    public void setConfig(String key, String value) {
        redisTemplate.opsForValue().set("system:config:" + key, value, 30, TimeUnit.DAYS);
    }

    @Override
    public void logOperation(Long userId, String operation, Map<String, Object> details) {
        // 记录操作日志（实际项目中应该保存到数据库或日志系统）
        System.out.println("Operation Log: User " + userId + " performed " + operation + " with details " + details);
    }

    @Override
    public void createAlert(String level, String message, Map<String, Object> details) {
        // 创建告警（实际项目中应该保存到数据库并发送通知）
        System.out.println("Alert: " + level + " - " + message + " - Details: " + details);
    }

    @Override
    public Long createTask(String name, String cron, Map<String, Object> params) {
        Long taskId = nextTaskId++;
        Map<String, Object> taskInfo = new HashMap<>();
        taskInfo.put("id", taskId);
        taskInfo.put("name", name);
        taskInfo.put("cron", cron);
        taskInfo.put("params", params);
        taskInfo.put("status", "created");

        tasks.put(taskId, taskInfo);
        return taskId;
    }
}
