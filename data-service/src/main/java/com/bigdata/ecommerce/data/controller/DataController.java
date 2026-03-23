package com.bigdata.ecommerce.data.controller;

import com.bigdata.ecommerce.data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping("/query")
    public ResponseEntity<?> queryData(@RequestBody QueryRequest queryRequest) {
        Map<String, Object> result = dataService.queryData(queryRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeData(@RequestBody AnalyzeRequest analyzeRequest) {
        Map<String, Object> result = dataService.analyzeData(analyzeRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/export")
    public ResponseEntity<?> exportData(@RequestBody ExportRequest exportRequest) {
        String exportUrl = dataService.exportData(exportRequest);
        return ResponseEntity.ok(exportUrl);
    }

    @PostMapping("/ingest")
    public ResponseEntity<?> ingestData(@RequestBody IngestRequest ingestRequest) {
        dataService.ingestData(ingestRequest);
        return ResponseEntity.ok("Data ingested successfully");
    }

    public static class QueryRequest {
        private String queryType;
        private Map<String, Object> parameters;

        public String getQueryType() {
            return queryType;
        }

        public void setQueryType(String queryType) {
            this.queryType = queryType;
        }

        public Map<String, Object> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, Object> parameters) {
            this.parameters = parameters;
        }
    }

    public static class AnalyzeRequest {
        private String analyzeType;
        private Map<String, Object> parameters;

        public String getAnalyzeType() {
            return analyzeType;
        }

        public void setAnalyzeType(String analyzeType) {
            this.analyzeType = analyzeType;
        }

        public Map<String, Object> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, Object> parameters) {
            this.parameters = parameters;
        }
    }

    public static class ExportRequest {
        private String exportType;
        private Map<String, Object> parameters;

        public String getExportType() {
            return exportType;
        }

        public void setExportType(String exportType) {
            this.exportType = exportType;
        }

        public Map<String, Object> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, Object> parameters) {
            this.parameters = parameters;
        }
    }

    public static class IngestRequest {
        private String dataType;
        private Map<String, Object> data;

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public Map<String, Object> getData() {
            return data;
        }

        public void setData(Map<String, Object> data) {
            this.data = data;
        }
    }
}
