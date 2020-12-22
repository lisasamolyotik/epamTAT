package com.example.wait;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomConditions {

    public static ExpectedCondition<Boolean> waitForLoad(){
        return driver -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
    }
}