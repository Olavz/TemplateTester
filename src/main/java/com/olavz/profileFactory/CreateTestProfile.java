package com.olavz.profileFactory;

import com.olavz.TestCriteria;
import com.olavz.TestProfile;
import org.yaml.snakeyaml.Yaml;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class CreateTestProfile {


    public static TestProfile fromYaml(String path) {
        TestProfile testProfile = new TestProfile();
        Yaml yaml = new Yaml();
        Map config = null;
        try {
            config = (Map) yaml.load(new FileInputStream(new File(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        testProfile.setTitle(config.get("title").toString());

        // Try to add a source for data retrieval.
        try {
            testProfile.setDataProviderPath(config.get("dataProviderPath").toString());
        } catch (NullPointerException e) {}
        try {
            testProfile.setDataProviderUrl(config.get("dataProviderUrl").toString());
        } catch (NullPointerException e) {}

        // Build TestCriteria's and add them to the profile.
        for(Map entry : ((List<Map>)config.get("testCriterias"))) {
            TestCriteria tc = new TestCriteria();
            if(getMapElement(entry, "testcase") != null) {
                tc.setReference(getMapElement(entry, "testcase"));
            }
            if(getMapElement(entry, "elementSelector") != null) {
                tc.setElementSelector(getMapElement(entry, "elementSelector"));
            }
            if(getMapElement(entry, "elementRequired") != null) {
                tc.setRequired(Boolean.parseBoolean(getMapElement(entry, "elementRequired")));
            }
            if(getMapElement(entry, "contentValidation") != null) {
                tc.setContentValidation(getMapElement(entry, "contentValidation"));
            }
            if(getMapElement(entry, "attributeSelector") != null) {
                tc.setAttributeSelector(getMapElement(entry, "attributeSelector"));
            }
            if(getMapElement(entry, "attributeValue") != null) {
                tc.setAttributeValue(getMapElement(entry, "attributeValue"));
            }
            if(getMapElement(entry, "description") != null) {
                tc.setDescription(getMapElement(entry, "description"));
            }
            testProfile.addTestCriteria(tc);
        }

        return null;
    }


    private static String getMapElement(Map map, String key) {
        if(map.get(key) != null) {
            return map.get(key).toString();
        }
        return null;
    }


    public static TestProfile fromJSON() {
        throw new NotImplementedException();
    }

    public static TestProfile fromXML() {
        throw new NotImplementedException();
    }


}
