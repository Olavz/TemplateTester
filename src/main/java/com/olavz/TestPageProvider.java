package com.olavz;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Olavz on 16.10.2016.
 */
public class TestPageProvider {


    public static JsonObject getJsonObjectFromUrl(String url) throws Exception {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return Json.createReader(rd).readObject();
        } finally {
            is.close();
        }
    }

    public static List<TestPage> getTestPagesFromUrl(String url) {
        JsonObject jsonObject = null;
        try {
            jsonObject = getJsonObjectFromUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TestPage> testPages = new ArrayList<>();

        Iterator iterator = jsonObject.getJsonArray("testPages").iterator();
        while (iterator.hasNext()) {
            JsonObject jo = (JsonObject) iterator.next();
            TestPage testPage = new TestPage();
            testPage.setDescription(jo.getString("name"));
            testPage.setUrl(jo.getString("url"));
            testPages.add(testPage);
        }

        return testPages;
    }

    public static JsonObject getJsonObjectFromPath(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return Json.createReader(br).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TestPage> getTestPagesFromFile(String path) {
        JsonObject jsonObject = null;
        try {
            jsonObject = getJsonObjectFromPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TestPage> testPages = new ArrayList<>();

        Iterator iterator = jsonObject.getJsonArray("testPages").iterator();
        while (iterator.hasNext()) {
            JsonObject jo = (JsonObject) iterator.next();
            TestPage testPage = new TestPage();
            testPage.setDescription(jo.getString("name"));
            testPage.setUrl(jo.getString("url"));
            testPages.add(testPage);
        }

        return testPages;
    }


}
