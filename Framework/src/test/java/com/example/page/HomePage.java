package com.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends AbstractPage {

    @FindBy(xpath = "//*[@class='search js-search d-header-top-menu-search']//input")
    private WebElement searchField;

    @FindBy(xpath = "//div[@class='button button_blue search__button js-search-button']")
    private WebElement searchButton;

    private final By searchFieldLocator = By.xpath("//*[@class='search js-search d-header-top-menu-search']//input");

    private final String PAGE_URL = "https://www.lamoda.by/";

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public HomePage openPage() {
        driver.get(PAGE_URL);
        return this;
    }

    public HomePage inputSearchKey(String key) {
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(searchFieldLocator))
                .sendKeys(key);
        return this;
    }

    public SearchResultPage search() {
        searchButton.click();
        return new SearchResultPage(driver);
    }
}
