package com.bigdata.ecommerce.data.service;

import com.bigdata.ecommerce.data.controller.DataController;

import java.util.Map;

public interface DataService {
    Map<String, Object> queryData(DataController.QueryRequest queryRequest);
    Map<String, Object> analyzeData(DataController.AnalyzeRequest analyzeRequest);
    String exportData(DataController.ExportRequest exportRequest);
    void ingestData(DataController.IngestRequest ingestRequest);
}
