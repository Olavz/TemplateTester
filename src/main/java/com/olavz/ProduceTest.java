package com.olavz;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProduceTest {


    public static TestCriteria produceTest(TestCriteria testCriteria, HtmlUnitDriver htmlUnitDriver) {

        /**
         * Populate the testCriteria with listMultipleElements and return the testCriteria with outcome of the test-suite.
         */

        List<HashMap<String, String>> listMultipleElements = new ArrayList();

        // A test criteria may find multiple elements, thus we need to validate all the matched elements.
        List<WebElement> elementList = FindElement.find(htmlUnitDriver, testCriteria.getElementSelector());
        HashMap<String, String> reportResultMap = new HashMap<>();
        boolean testHasFailure = false;

        if(elementList != null) {
            int counter = 0;
            for(WebElement element : elementList) {
                counter++;
                // For multiple entries, we get multiple results.
                // Step 1: Validate element type
                boolean validElementType = false;
                if(testCriteria.getElementType() == TestCriteria.ElementType.NOT_APPLICABLE) {
                    validElementType = true; // Valid as no validation is required.
                } else if(testCriteria.getElementType() == TestCriteria.ElementType.TEXT) {
                    validElementType = isText(element.getText());
                    if(validElementType) {
                        reportResultMap.put("ELEMENT_VALIDATE_TEXT_" + counter, "SUCCESS");
                    } else {
                        reportResultMap.put("ELEMENT_VALIDATE_TEXT_" + counter, "FAILURE");
                        testHasFailure = true;
                    }
                } else if(testCriteria.getElementType() == TestCriteria.ElementType.NUMBER) {
                    validElementType = isNumeric(element.getText());
                    if(validElementType) {
                        reportResultMap.put("ELEMENT_VALIDATE_NUMBER_" + counter, "SUCCESS");
                    } else {
                        reportResultMap.put("ELEMENT_VALIDATE_NUMBER_" + counter, "FAILURE");
                        testHasFailure = true;
                    }
                }


                if(testCriteria.isAttributeValidation()) {
                    String attributeValue = element.getAttribute(testCriteria.getAttributeSelector());
                    if(attributeValue != null) {
                        if(attributeValue.equals(testCriteria.getAttributeValue())) {
                            // attribute validation success. Matched attribute value.
                            reportResultMap.put("ATTRIBUTE_VALIDATION_" + counter, "SUCCESS");
                        } else {
                            // attribute validation failed. Could not match attribute value.
                            reportResultMap.put("ATTRIBUTE_VALIDATION_MISS_MATCH_" + counter, "FAILURE");
                            testHasFailure = true;
                        }
                    } else {
                        // attribute validation failed. Attribute not found.
                        reportResultMap.put("ATTRIBUTE_VALIDATION_NO_ATTRIBUTE_" + counter, "FAILURE");
                        testHasFailure = true;
                    }
                }
                listMultipleElements.add(reportResultMap);
            }

            if(elementList.size() == 0 && testCriteria.isRequired()) {
                // Error: No element found, but is required.
                reportResultMap.put("ELEMENT_REQUIRED", "FAILURE");
                testHasFailure = true;
                listMultipleElements.add(reportResultMap);
            } else if (elementList.size() > 0 && testCriteria.isRequired()) {
                reportResultMap.put("ELEMENT_REQUIRED", "SUCCESS");
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
