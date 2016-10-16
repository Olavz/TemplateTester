package com.olavz;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;

/**
 * Created by Olavz on 13.10.2016.
 */
public class FindElement {


    public static List<WebElement> find(HtmlUnitDriver htmlUnitDriver, String expression) {

        // Determine "By" expression #id or .class
        char prefix = expression.charAt(0);
        String selector = expression.substring(1);
        By byQuery = null;
        if(prefix == '#') {
            // Select by ID
            byQuery = By.id(selector);
        } else if(prefix == '.') {
            // Select by Class
            byQuery = By.className(selector);
        } else {
            // Not a valid prefix, could not find.
            return null;
        }


        List<WebElement> element = null;
        try {
            element = htmlUnitDriver.findElements(byQuery);
        } catch (NoSuchElementException e) {
            // Dont do anything, element will be null and thats okay for now.
        }

        return element;
    }


}
