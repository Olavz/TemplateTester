package com.olavz;

import java.util.HashMap;
import java.util.List;

public class TestCriteria implements Cloneable {

    private String reference = "1001";
    private boolean isRequired = true;
    private String elementSelector = "";
    private String contentValidation = "";
    private String attributeSelector = null;
    private String attributeValue = null;
    private String description = "";
    List<HashMap<String, String>> listMultipleElementResults;
    private boolean hasFailed = false;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isAttributeValidation() {
        if(attributeSelector != null && attributeValue != null) {
            return true;
        }
        return false;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public String getElementSelector() {
        return elementSelector;
    }

    public void setElementSelector(String elementSelector) {
        this.elementSelector = elementSelector;
    }

    public String getAttributeSelector() {
        return attributeSelector;
    }

    public void setAttributeSelector(String attributeSelector) {
        this.attributeSelector = attributeSelector;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentValidation() {
        return contentValidation;
    }

    public void setContentValidation(String contentValidation) {
        this.contentValidation = contentValidation;
    }

    public List<HashMap<String, String>> getListMultipleElementResults() {
        return listMultipleElementResults;
    }

    public void setListMultipleElementResults(List<HashMap<String, String>> listMultipleElementResults) {
        this.listMultipleElementResults = listMultipleElementResults;
    }

    public boolean hasFailed() {
        return hasFailed;
    }

    public void setFailed(boolean hasFailed) {
        this.hasFailed = hasFailed;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
