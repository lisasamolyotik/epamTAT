package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends AbstractPage {
    @FindBy(xpath = "//input[@placeholder='Поиск']")
    private WebElement searchField;

    @FindBy(xpath = "//div[@class='button button_blue search__button js-search-button']")
    private WebElement searchButton;

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
        searchField.sendKeys(key);
        return this;
    }

    public SearchResultPage search() {
        searchButton.click();
        return new SearchResultPage(driver);
    }
}
