package com.example.page;

import com.example.type.CountryType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends AbstractPage {

    @FindBy(xpath = "//*[@class='search js-search d-header-top-menu-search']//input")
    private WebElement searchField;

    @FindBy(xpath = "//div[@class='button button_blue search__button js-search-button']")
    private WebElement searchButton;

    private final By searchFieldLocator = By.xpath("//*[@class='search js-search d-header-top-menu-search']//input");
    private final By changeCountryButtonLocator = By.xpath("//div[@class='footer__local']//span[2]");

    private final String countryButtonPathPattern = "//body/div[last()]//a[%d]";

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
        driver.findElement(searchFieldLocator)
                .sendKeys(key);
        logger.info("wrote search key - " + key);
        return this;
    }

    public SearchResultPage search() {
        searchButton.click();
        logger.info("performed search");
        return new SearchResultPage(driver);
    }

    public HomePage openChangeCountryDetails() {
        click(driver.findElement(changeCountryButtonLocator));
        logger.info("opened change country details");
        return this;
    }

    public HomePage selectCountry(CountryType country) {
        click(driver.findElement(buildCountryButtonLocator(country)));
        logger.info("changed country to - " + country);
        return this;
    }

    public String getCurrentUrl() {
        String currentUrl = driver.getCurrentUrl();
        logger.info("current url - " + currentUrl);
        return currentUrl;
    }

    private By buildCountryButtonLocator(CountryType country) {
        return By.xpath(String.format(countryButtonPathPattern, country.ordinal() + 1));
    }
}
