package com.bigdata.ecommerce.security.service.impl;

import com.bigdata.ecommerce.security.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String encrypt(String data, String key) {
        // 简单的Base64加密（实际项目中应该使用更安全的加密算法）
        byte[] encryptedBytes = (data + "|" + key).getBytes();
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String data, String key) {
        // 简单的Base64解密
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        String decodedString = new String(decodedBytes);
        String[] parts = decodedString.split("|", 2);
        if (parts.length == 2 && parts[1].equals(key)) {
            return parts[0];
        }
        return "";
    }

    @Override
    public String mask(String data, String maskType) {
        switch (maskType) {
            case "phone":
                return maskPhone(data);
            case "email":
                return maskEmail(data);
            case "idcard":
                return maskIdCard(data);
            default:
                return maskDefault(data);
        }
    }

    @Override
    public void audit(Long userId, String action, Map<String, Object> details) {
        // 记录审计日志（实际项目中应该保存到数据库或日志系统）
        System.out.println("Audit: User " + userId + " performed action " + action + " with details " + details);
    }

    private String maskPhone(String phone) {
        if (phone.length() >= 11) {
            return phone.substring(0, 3) + "****" + phone.substring(7);
        }
        return phone;
    }

    private String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex > 2) {
            return email.substring(0, 2) + "****" + email.substring(atIndex);
        }
        return email;
    }

    private String maskIdCard(String idCard) {
        if (idCard.length() >= 15) {
            return idCard.substring(0, 3) + "********" + idCard.substring(11);
        }
        return idCard;
    }

    private String maskDefault(String data) {
        if (data.length() > 4) {
            return data.substring(0, 2) + "****" + data.substring(data.length() - 2);
        }
        return "****";
    }
}
