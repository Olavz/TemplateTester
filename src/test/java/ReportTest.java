import com.olavz.TestPage;
import com.olavz.TestPageProvider;
import com.olavz.TestProfile;
import com.olavz.report.Report;
import com.olavz.report.ReportUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ReportTest {

    @Test
    public void makeReportFromTestProfiles() {
        Report report = new Report();

        // Add test profiles to the report.
        report.addTestProfile(getTicketsTestProfile());
        report.addTestProfile(getNewsTestProfile());

        String htmlReport = report.processReportAndProduceOutcome();
        try {
            ReportUtil.makeTestReport(htmlReport);
        } catch (Exception e) {

        }

        Assert.assertEquals(report.hasFailure(), false);
    }

    private TestProfile getTicketsTestProfile() {
        TestProfile tp = new TestProfile();
        tp.setTitle("Tickets");

        tp.addTestCriteria("Ref 1", "#order-number", true, "All tickets should have ordernumber.");
        tp.addTestCriteria("Ref 2", ".tdName", true, "All tickets should have at least one name.");
        tp.addTestCriteria("Ref 3", "#other-remarks", false, "Tickets can have remarks, but not required.");
        tp.addTestCriteria("Ref 3.1", ".remark-beer", false, "If the ticket have a beer remark, it should have a beer icon.",
                "", "icon", "beer.png");
        tp.addTestCriteria("Ref 3.2", ".remark-group-discount", false, "If ticket have group discount, it should be a text.",
                "text");
        tp.addTestCriteria("Ref 3.3", ".remark-number", false, "If ticket have a number remark, the content should be numeric.",
                "number");
        tp.addTestCriteria("Ref 4", ".tdAmount", true, "Check that amount is correct. [0-9] [NOK|EURO]", "[0-9] [NOK|EURO]");


        List<TestPage> testPages = TestPageProvider.getTestPagesFromFile("src/test/resources/insanity-tests/tickets/fileProvider.json");
        tp.addTestPages(testPages);

        tp.runTest();
        return tp;
    }

    private TestProfile getNewsTestProfile() {

        // TODO: Complete this example profile.

        TestProfile tp = new TestProfile();
        tp.setTitle("Check imported news articles");

        tp.addTestCriteria("First article", ".test", false, "Empty test");

        List<TestPage> testPages = TestPageProvider.getTestPagesFromFile("src/test/resources/insanity-tests/news/fileProvider.json");
        tp.addTestPages(testPages);

        tp.runTest();
        return tp;
    }



}