package com.olavz;

import java.util.List;

public class TestPage {

    private String description;
    private String url;
    private List<TestCriteria> testCriteriasWithResult;
    private boolean hasFailed = false;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<TestCriteria> getTestCriteriasWithResult() {
        return testCriteriasWithResult;
    }

    public void setTestCriteriasWithResult(List<TestCriteria> testCriteriasWithResult) {
        this.testCriteriasWithResult = testCriteriasWithResult;
    }

    public boolean hasFailed() {
        return hasFailed;
    }

    public void setFailed(boolean hasFailed) {
        this.hasFailed = hasFailed;
    }
}
