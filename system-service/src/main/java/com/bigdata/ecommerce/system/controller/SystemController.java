package com.bigdata.ecommerce.system.controller;

import com.bigdata.ecommerce.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @GetMapping("/config/{key}")
    public ResponseEntity<?> getConfig(@PathVariable String key) {
        String value = systemService.getConfig(key);
        return ResponseEntity.ok(value);
    }

    @PostMapping("/config")
    public ResponseEntity<?> setConfig(@RequestBody ConfigRequest configRequest) {
        systemService.setConfig(configRequest.getKey(), configRequest.getValue());
        return ResponseEntity.ok("Config set successfully");
    }

    @PostMapping("/log")
    public ResponseEntity<?> logOperation(@RequestBody LogRequest logRequest) {
        systemService.logOperation(logRequest.getUserId(), logRequest.getOperation(), logRequest.getDetails());
        return ResponseEntity.ok("Log recorded successfully");
    }

    @PostMapping("/alert")
    public ResponseEntity<?> createAlert(@RequestBody AlertRequest alertRequest) {
        systemService.createAlert(alertRequest.getLevel(), alertRequest.getMessage(), alertRequest.getDetails());
        return ResponseEntity.ok("Alert created successfully");
    }

    @PostMapping("/task")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest) {
        Long taskId = systemService.createTask(taskRequest.getName(), taskRequest.getCron(), taskRequest.getParams());
        return ResponseEntity.ok(taskId);
    }

    public static class ConfigRequest {
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class LogRequest {
        private Long userId;
        private String operation;
        private Map<String, Object> details;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public Map<String, Object> getDetails() {
            return details;
        }

        public void setDetails(Map<String, Object> details) {
            this.details = details;
        }
    }

    public static class AlertRequest {
        private String level;
        private String message;
        private Map<String, Object> details;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Map<String, Object> getDetails() {
            return details;
        }

        public void setDetails(Map<String, Object> details) {
            this.details = details;
        }
    }

    public static class TaskRequest {
        private String name;
        private String cron;
        private Map<String, Object> params;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }
    }
}
