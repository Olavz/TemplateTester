package com.olavz;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProduceTest {


    public static TestCriteria produceTest(TestCriteria testCriteria, HtmlUnitDriver htmlUnitDriver) {

        /**
         * Populate the testCriteria with listMultipleElements and return the testCriteria with outcome of the test-suite.
         */

        List<HashMap<String, String>> listMultipleElements = new ArrayList();

        // A test criteria may find multiple elements, thus we need to validate all the matched elements.
        List<WebElement> elementList = FindElement.find(htmlUnitDriver, testCriteria.getElementSelector());
        boolean testHasFailure = false;

        if(elementList != null) {
            for(WebElement element : elementList) {
                HashMap<String, String> reportResultMap = new HashMap<>();
                // For multiple entries, we get multiple results.
                // Step 1: Validate element type
                boolean validElementType = false;
                if(testCriteria.getContentValidation().equals("")) {
                    validElementType = true; // Valid as no validation is required.
                } else if(testCriteria.getContentValidation().equalsIgnoreCase("TEXT")) {
                    validElementType = isText(element.getText());
                    if(validElementType) {
                        reportResultMap.put("ELEMENT_VALIDATE_TEXT", "SUCCESS");
                    } else {
                        reportResultMap.put("ELEMENT_VALIDATE_TEXT", "FAILURE");
                        testHasFailure = true;
                    }
                } else if(testCriteria.getContentValidation().equalsIgnoreCase("NUMBER") ||
                        testCriteria.getContentValidation().equalsIgnoreCase("NUMERIC")) {
                    validElementType = isNumeric(element.getText());
                    if(validElementType) {
                        reportResultMap.put("ELEMENT_VALIDATE_NUMBER", "SUCCESS");
                    } else {
                        reportResultMap.put("ELEMENT_VALIDATE_NUMBER", "FAILURE");
                        testHasFailure = true;
                    }
                } else {
                    // Try to apply content validation as regex.
                    String p = element.getText();
                    Pattern pattern = Pattern.compile(testCriteria.getContentValidation(), Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(element.getText());
                    if(matcher.find()) {
                        reportResultMap.put("ELEMENT_CONTENT_VALIDATION_REGEX", "SUCCESS");
                    } else {
                        reportResultMap.put("ELEMENT_CONTENT_VALIDATION_REGEX", "FAILURE");
                        testHasFailure = true;
                    }
                }


                if(testCriteria.isAttributeValidation()) {
                    String attributeValue = element.getAttribute(testCriteria.getAttributeSelector());
                    if(attributeValue != null) {
                        if(attributeValue.equals(testCriteria.getAttributeValue())) {
                            // attribute validation success. Matched attribute value.
                            reportResultMap.put("ATTRIBUTE_VALIDATION", "SUCCESS");
                        } else {
                            // attribute validation failed. Could not match attribute value.
                            reportResultMap.put("ATTRIBUTE_VALIDATION_MISS_MATCH", "FAILURE");
                            testHasFailure = true;
                        }
                    } else {
                        // attribute validation failed. Attribute not found.
                        reportResultMap.put("ATTRIBUTE_VALIDATION_NO_ATTRIBUTE", "FAILURE");
                        testHasFailure = true;
                    }
                }
                listMultipleElements.add(reportResultMap);
            }

            if(elementList.size() == 0 && testCriteria.isRequired()) {
                // Error: No element found, but is required.
                HashMap<String, String> elmRequiredMap = new HashMap<>();
                elmRequiredMap.put("ELEMENT_REQUIRED", "FAILURE");
                listMultipleElements.add(elmRequiredMap);
                testHasFailure = true;
            } else if (elementList.size() > 0 && testCriteria.isRequired()) {
                HashMap<String, String> elmRequiredMap = new HashMap<>();
                elmRequiredMap.put("ELEMENT_REQUIRED", "SUCCESS");
                listMultipleElements.add(elmRequiredMap);
            } else if(elementList.size() > 0 && !testCriteria.isRequired()) {
                HashMap<String, String> elmRequiredMap = new HashMap<>();
                elmRequiredMap.put("ELEMENT_NOT_REQUIRED_BUT_FOUND", "SUCCESS");
                listMultipleElements.add(elmRequiredMap);
            }

            // Flag the overall process. If any failure detected, it should be flagged so.
            testCriteria.setFailed(testHasFailure);
        }

        testCriteria.setListMultipleElementResults(listMultipleElements);
        return testCriteria;
    }

    private static boolean isText(String text) {
        if(text.length() > 0) {
            return true;
        }
        return false;
    }

    private static boolean isNumeric(String text) {
        return StringUtils.isNumeric(text);
    }

}
