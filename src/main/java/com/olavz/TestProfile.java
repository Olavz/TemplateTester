package com.olavz;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.List;

public class TestProfile {

    /**
     * TestProfile contains a set of TestCriterias that make up for a test profile.
     */

    private String title = "";
    private List<TestCriteria> testCriterias;
    private boolean testProfileHasFailure = false;

    HtmlUnitDriver htmlUnitDriver;
    private List<TestPage> testPages;

    public TestProfile() {
        testCriterias = new ArrayList();
        testPages = new ArrayList<>();

        // Declaring and initialising the HtmlUnitWebDriver
        htmlUnitDriver = new HtmlUnitDriver();
        htmlUnitDriver.setJavascriptEnabled(true);
    }

    public void addTestPages(List<TestPage> testPages) {
        this.testPages.addAll(testPages);
    }

    public void addTestPage(String description, String url) {
        TestPage testPage = new TestPage();
        testPage.setDescription(description);
        testPage.setUrl(url);
        testPages.add(testPage);
    }

    public void runTest() {

        for(TestPage testPage : testPages) {
            htmlUnitDriver.get(testPage.getUrl()); // Fetch page from url.
            List<TestCriteria> testCriteriasWithResult = new ArrayList<>();
            boolean pageContainsFailure = false;
            for(TestCriteria testCriteria : testCriterias) {
                TestCriteria result = ProduceTest.produceTest(testCriteria, htmlUnitDriver);
                try {
                    TestCriteria testCriteriaWithResult = (TestCriteria) result.clone();
                    if(testCriteriaWithResult.hasFailed()) {
                        pageContainsFailure = true;
                    }
                    testCriteriasWithResult.add(testCriteriaWithResult); // Cloning the test criteria.
                } catch (CloneNotSupportedException ex) {

                }
            }
            testPage.setFailed(pageContainsFailure);
            testPage.setTestCriteriasWithResult(testCriteriasWithResult);

            if(pageContainsFailure) {
                testProfileHasFailure = true;
            }
        }

    }


    public void addTestCriteria(TestCriteria tc) {
        testCriterias.add(tc);
    }

    public void addTestCriteria(String reference, String elementSelector, boolean elementRequired, String description) {
        TestCriteria tc = new TestCriteria();
        tc.setReference(reference);
        tc.setElementSelector(elementSelector);
        tc.setRequired(elementRequired);
        tc.setDescription(description);
        testCriterias.add(tc);
    }

    public void addTestCriteria(String reference, String elementSelector, boolean elementRequired, String description,
                                TestCriteria.ElementType contentValidation) {
        TestCriteria tc = new TestCriteria();
        tc.setReference(reference);
        tc.setElementSelector(elementSelector);
        tc.setRequired(elementRequired);
        tc.setDescription(description);
        tc.setElementType(contentValidation);
        testCriterias.add(tc);
    }

    public void addTestCriteria(String reference, String elementSelector, boolean elementRequired, String description,
                                TestCriteria.ElementType contentValidation, String attributeSelector, String attributeValue ) {
        TestCriteria tc = new TestCriteria();
        tc.setReference(reference);
        tc.setElementSelector(elementSelector);
        tc.setRequired(elementRequired);
        tc.setDescription(description);
        tc.setElementType(contentValidation);
        tc.setAttributeSelector(attributeSelector);
        tc.setAttributeValue(attributeValue);
        testCriterias.add(tc);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TestPage> getTestPages() {
        return testPages;
    }

    public void setTestPages(List<TestPage> testPages) {
        this.testPages = testPages;
    }

    public boolean hasFailure() {
        return testProfileHasFailure;
    }

    public void setFailure(boolean testProfileHasFailure) {
        this.testProfileHasFailure = testProfileHasFailure;
    }
}
