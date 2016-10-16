package com.olavz.report;

import com.olavz.TestCriteria;
import com.olavz.TestPage;
import com.olavz.TestProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Report {

    private String title = "Generic Selenium test";
    private List<TestProfile> profileList = new ArrayList<>();
    private boolean reportHasFailure = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addTestProfile(TestProfile testProfile) {
        profileList.add(testProfile);

        // Check for failure each time we add a test profile.
        if(testProfile.hasFailure()) {
            reportHasFailure = true;
        }
    }

    private String getStatusImage(boolean hasFailed) {
        String source;
        if(hasFailed) {
            source = "test-classes/img/failure.png";
        } else {
            source = "test-classes/img/success.png";
        }
        return "<img src=\""+source+"\" />";
    }

    public String processReportAndProduceOutcome() {
        StringBuilder sb = new StringBuilder();

        for (TestProfile testProfile : profileList) {
            sb.append("<div class=\"profile\">");
            sb.append("<h1>Profile: " + testProfile.getTitle() + "</h1>");
            for (TestPage testPage : testProfile.getTestPages()) {
                sb.append("<div class=\"test-page\">");
                sb.append("<div class=\"row h2ShowCriterias\">" +
                        "<div class=\"col-sm-5\">" + getStatusImage(testPage.hasFailed()) + testPage.getDescription() + "</div> " +
                        "<div class=\"col-sm-7\">" + testPage.getUrl() + "</div>" +
                        "</div>");
                sb.append("<div class=\"test-criterias\" style=\"padding-bottom: 10px;\">");
                for (TestCriteria testCriteria : testPage.getTestCriteriasWithResult()) {
                    sb.append("<div>");
                    sb.append("<div class=\"row h3ShowCriteria\">" +
                            "<div class=\"col-sm-2 offset-sm-1\">" + getStatusImage(testCriteria.hasFailed()) + testCriteria.getReference() +"</div>" +
                            "<div class=\"col-sm-9\"> " + testCriteria.getDescription() + "</div>" +
                            "</div>");

                    sb.append("<div class=\"test-criteria\">");
                    for (HashMap<String, String> singleElement : testCriteria.getListMultipleElementResults()) {
                        for (HashMap.Entry<String, String> entry : singleElement.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            sb.append("<div class=\"row\">" +
                                    "<div class=\"col-sm-5 offset-sm-3\">" + key + "</div><div col-sm-4>"+value+"</div>" +
                                    "</div>");
                        }
                    }
                    sb.append("</div>");
                    sb.append("</div>");
                }
                sb.append("</div>");
                sb.append("</div>");
            }
            sb.append("<div>");
        }


        return sb.toString();
    }

    public boolean hasFailure() {
        return reportHasFailure;
    }
}
