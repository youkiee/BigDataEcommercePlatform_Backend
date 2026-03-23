package com.bigdata.ecommerce.risk.service;

import com.bigdata.ecommerce.risk.controller.RiskController;

import java.util.Map;

public interface RiskService {
    Map<String, Object> scanRisk(RiskController.RiskRequest riskRequest);
    double getRiskScore(Long userId);
    Long createCase(RiskController.CaseRequest caseRequest);
    Map<String, Object> getCase(Long caseId);
}
