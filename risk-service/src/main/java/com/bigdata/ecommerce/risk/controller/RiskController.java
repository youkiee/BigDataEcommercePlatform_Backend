package com.bigdata.ecommerce.risk.controller;

import com.bigdata.ecommerce.risk.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/risk")
public class RiskController {

    @Autowired
    private RiskService riskService;

    @PostMapping("/scan")
    public ResponseEntity<?> scanRisk(@RequestBody RiskRequest riskRequest) {
        Map<String, Object> riskResult = riskService.scanRisk(riskRequest);
        return ResponseEntity.ok(riskResult);
    }

    @GetMapping("/score/{userId}")
    public ResponseEntity<?> getRiskScore(@PathVariable Long userId) {
        double score = riskService.getRiskScore(userId);
        return ResponseEntity.ok(score);
    }

    @PostMapping("/case/create")
    public ResponseEntity<?> createCase(@RequestBody CaseRequest caseRequest) {
        Long caseId = riskService.createCase(caseRequest);
        return ResponseEntity.ok(caseId);
    }

    @GetMapping("/case/{caseId}")
    public ResponseEntity<?> getCase(@PathVariable Long caseId) {
        Map<String, Object> caseInfo = riskService.getCase(caseId);
        return ResponseEntity.ok(caseInfo);
    }

    public static class RiskRequest {
        private Long userId;
        private String action;
        private Map<String, Object> context;

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

        public Map<String, Object> getContext() {
            return context;
        }

        public void setContext(Map<String, Object> context) {
            this.context = context;
        }
    }

    public static class CaseRequest {
        private Long userId;
        private String riskType;
        private String description;
        private Map<String, Object> evidence;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getRiskType() {
            return riskType;
        }

        public void setRiskType(String riskType) {
            this.riskType = riskType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Map<String, Object> getEvidence() {
            return evidence;
        }

        public void setEvidence(Map<String, Object> evidence) {
            this.evidence = evidence;
        }
    }
}
