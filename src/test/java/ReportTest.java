import com.olavz.*;
import com.olavz.report.Report;
import com.olavz.report.ReportUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ReportTest {

    @Test
    public void testEasy() {
        // Declaring and initialising the HtmlUnitWebDriver
        HtmlUnitDriver unitDriver = new HtmlUnitDriver();

        // open google.com webpage
        unitDriver.get("http://javatest.olavz.com/templates/page1.html");

        // find the search edit box on the google page
        WebElement searchBox = unitDriver.findElement(By.className("hello"));

        WebElement searchBox2 = unitDriver.findElement(By.id("world"));

    }


    @Test
    public void findElement() {
        // Declaring and initialising the HtmlUnitWebDriver
        HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver();
        htmlUnitDriver.get("http://javatest.olavz.com/templates/page1.html");

        List<WebElement> test = FindElement.find(htmlUnitDriver, "#myList");

        List<WebElement> test2 = FindElement.find(htmlUnitDriver, ".liElement");
    }

    @Test
    public void tt() {
        ReportUtil.getBasicTemplate();
    }

    @Test
    public void makeTestProfile() {
        TestProfile tp = new TestProfile();
        tp.setTitle("Tests");

        // Add criterias to profiles
        tp.addTestCriteria("1001", ".liElement", true, "Check for .liElement");
        tp.addTestCriteria("1002", ".b", true, "Check for bold text created by JavaScript");
        tp.addTestCriteria("1003", "#myList", true, "Check for attribute data-tag=\"yes\"", TestCriteria.ElementType.NOT_APPLICABLE, "data-tag", "yes");

        // Add test pages from url provider.
        List<TestPage> testPages = TestPageProvider.getTestPagesFromUrl("http://javatest.olavz.com/templates/urlProvider.php");
        tp.addTestPages(testPages);

        tp.runTest(); // Run the tests

        Report report = new Report();
        report.addTestProfile(tp);

        String htmlReport = report.processReportAndProduceOutcome();
        try {
            ReportUtil.makeTestReport(htmlReport);
        } catch (Exception e) {

        }

        Assert.assertEquals(report.hasFailure(), false);
    }

    @Test
    public void CheckTestCase1() {
        // Declaring and initialising the HtmlUnitWebDriver
        HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver();
        htmlUnitDriver.get("http://javatest.olavz.com/templates/page1.html");

        TestCriteria tc = new TestCriteria();
        tc.setReference("1001");
        tc.setDescription("First testcase");
        tc.setElementSelector(".liElement");
        tc.setRequired(true);
        tc.setElementType(TestCriteria.ElementType.NOT_APPLICABLE);

        TestCriteria result = ProduceTest.produceTest(tc, htmlUnitDriver);
    }

    @Test
    public void CheckTestCase2() {
        // Declaring and initialising the HtmlUnitWebDriver
        HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver();
        htmlUnitDriver.get("http://javatest.olavz.com/templates/page1.html");

        TestCriteria tc = new TestCriteria();
        tc.setReference("1002");
        tc.setDescription("Second testcase");
        tc.setElementSelector("#myList");
        tc.setRequired(true);
        tc.setElementType(TestCriteria.ElementType.NOT_APPLICABLE);
        tc.setAttributeSelector("data-tag");
        tc.setAttributeValue("yes");

        TestCriteria tc2 = new TestCriteria();
        tc2.setReference("1001");
        tc2.setDescription("First testcase");
        tc2.setElementSelector(".liElement");
        tc2.setRequired(true);
        tc2.setElementType(TestCriteria.ElementType.NOT_APPLICABLE);

        TestCriteria result = ProduceTest.produceTest(tc, htmlUnitDriver);
        TestCriteria result2 = ProduceTest.produceTest(tc2, htmlUnitDriver);
    }


}