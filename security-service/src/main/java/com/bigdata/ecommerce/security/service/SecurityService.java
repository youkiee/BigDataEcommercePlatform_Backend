package com.bigdata.ecommerce.security.service;

import java.util.Map;

public interface SecurityService {
    String encrypt(String data, String key);
    String decrypt(String data, String key);
    String mask(String data, String maskType);
    void audit(Long userId, String action, Map<String, Object> details);
}
