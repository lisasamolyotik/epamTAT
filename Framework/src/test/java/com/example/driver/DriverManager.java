package com.example.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (null == driver) {
            /*switch (System.getProperty("browser")) {
                case "chrome": {*/
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    //options.addArguments("--headless");
                    driver = new ChromeDriver(options);
                /*}
                default: {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions options = new FirefoxOptions();
                    options.addArguments("--headless");
                    driver = new FirefoxDriver(options);
                }
            }*/
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }
        return driver;
    }

    public static void closeDriver() {
        driver.quit();
        driver = null;
    }
}
