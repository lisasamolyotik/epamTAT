package com.example.utils;

import org.openqa.selenium.WebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceMatcher {
    private static Double defaultPriceValue = 0.0;

    public static Double getPriceFromWebElement(WebElement webElement) {
        Matcher matcher = Pattern.compile("\\d+\\s\\d+\\.\\d{2}").matcher(webElement.getText());
        if (matcher.find()) {
            return Double.parseDouble(matcher.group().replaceAll("\\s+", ""));
        }
        return defaultPriceValue;
    }
}
