package com.bigdata.ecommerce.system.service;

import java.util.Map;

public interface SystemService {
    String getConfig(String key);
    void setConfig(String key, String value);
    void logOperation(Long userId, String operation, Map<String, Object> details);
    void createAlert(String level, String message, Map<String, Object> details);
    Long createTask(String name, String cron, Map<String, Object> params);
}
