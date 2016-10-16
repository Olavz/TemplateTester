package com.olavz.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ReportUtil {

    public static String getBasicTemplate() {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/report_template.html"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static void makeTestReport(String input) throws IOException {

        String template = getBasicTemplate();
        template = template.replace("{{content}}", input);

        List<String> lines = Arrays.asList(template);
        Path file = Paths.get("target/test-classes/template-test-report.html");
        Files.write(file, lines, Charset.forName("UTF-8"));
    }


}
