package com.bigdata.ecommerce.security.controller;

import com.bigdata.ecommerce.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/encrypt")
    public ResponseEntity<?> encryptData(@RequestBody EncryptRequest encryptRequest) {
        String encryptedData = securityService.encrypt(encryptRequest.getData(), encryptRequest.getKey());
        return ResponseEntity.ok(encryptedData);
    }

    @PostMapping("/decrypt")
    public ResponseEntity<?> decryptData(@RequestBody DecryptRequest decryptRequest) {
        String decryptedData = securityService.decrypt(decryptRequest.getData(), decryptRequest.getKey());
        return ResponseEntity.ok(decryptedData);
    }

    @PostMapping("/mask")
    public ResponseEntity<?> maskData(@RequestBody MaskRequest maskRequest) {
        String maskedData = securityService.mask(maskRequest.getData(), maskRequest.getMaskType());
        return ResponseEntity.ok(maskedData);
    }

    @PostMapping("/audit")
    public ResponseEntity<?> auditAction(@RequestBody AuditRequest auditRequest) {
        securityService.audit(auditRequest.getUserId(), auditRequest.getAction(), auditRequest.getDetails());
        return ResponseEntity.ok("Audit record created successfully");
    }

    public static class EncryptRequest {
        private String data;
        private String key;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class DecryptRequest {
        private String data;
        private String key;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class MaskRequest {
        private String data;
        private String maskType;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getMaskType() {
            return maskType;
        }

        public void setMaskType(String maskType) {
            this.maskType = maskType;
        }
    }

    public static class AuditRequest {
        private Long userId;
        private String action;
        private Map<String, Object> details;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Map<String, Object> getDetails() {
            return details;
        }

        public void setDetails(Map<String, Object> details) {
            this.details = details;
        }
    }
}
