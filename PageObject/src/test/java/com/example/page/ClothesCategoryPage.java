package com.example.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClothesCategoryPage extends AbstractPage {

    @FindBy(xpath = "//span[@class='products-catalog__sort']//span[@class='button button_right button_wo-pdng-r']")
    private WebElement sortTypeButton;

    private By sortPriceAscendingButtonLocator = By.xpath("//li[@data-order='price_asc']");

    @FindBy(xpath = "//li[@data-order='price_asc']")
    private WebElement sortPriceAscendingButton;

    @FindAll(@FindBy(xpath = "//div[@class='products-list-item']"))
    private List<WebElement> sortResults;

    private final String PAGE_URL = "https://www.lamoda.by/c/355/clothes-zhenskaya-odezhda/?sitelink=topmenuW&l=2";

    public ClothesCategoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public ClothesCategoryPage openPage() {
        driver.get(PAGE_URL);
        return this;
    }

    public ClothesCategoryPage openSortTypes() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", sortTypeButton);

        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(sortTypeButton)).click();
        return this;
    }

    public ClothesCategoryPage sortByPriceAscending() {
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(sortPriceAscendingButtonLocator))
                .click();
        return this;
    }

    public List<Double> getPrices() {
        return sortResults.stream().map(item -> {
            Matcher matcher = Pattern.compile("\\d+.\\d{2}").matcher(item.getText());
            List<Double> itemPrices = new ArrayList<>();
            while (matcher.find()) {
                itemPrices.add(Double.valueOf(matcher.group()));
            }
            return itemPrices.stream().min(Double::compareTo).orElse(.0);
        }).collect(Collectors.toList());
    }
}
