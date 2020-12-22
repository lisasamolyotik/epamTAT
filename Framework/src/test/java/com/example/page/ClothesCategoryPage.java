package com.example.page;

import com.example.model.ProductItem;
import com.example.wait.CustomConditions;
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

    @FindBy(xpath = "//li[@data-order='price_asc']")
    private WebElement sortPriceAscendingButton;

    @FindAll(@FindBy(xpath = "//*[@class='products-catalog__list']"))
    private WebElement sortResultsList;

    @FindBy(xpath = "//div[@class='multifilter multifilter_price']/div/span")
    private WebElement filterByPriceButton;

    @FindBy(xpath = "//div[@class='multifilter multifilter_brands']/div/span")
    private WebElement filterByBrandButton;

    @FindBy(xpath = "//input[@class='text-field range__value range__value_right']")
    private WebElement maxFilterRangeField;

    @FindBy(xpath = "//input[@class='text-field']")
    private WebElement filterByBrandField;

    @FindBy(xpath = "//div[@class='multifilter multifilter_brands']//label[@class='multifilter-item__label']")
    private WebElement multiFilterItems;

    @FindBy(xpath = "//div[@class='multifilter multifilter_brands']//div[@class='dropdown']")
    private WebElement brandsMultiFilterContainer;

    @FindBy(xpath = "//div[@class='multifilter multifilter_price']//span[@class='button button_s button_blue multifilter-actions__apply']")
    private WebElement applyFilterByPriceButton;

    @FindBy(xpath = "//div[@class='multifilter multifilter_brands']//span[@class='button button_s button_blue multifilter-actions__apply']")
    private WebElement applyFilterByBrandButton;

    /*@FindBy(xpath = "//div[@class='multifilter multifilter_brands']//label[@class='multifilter-item__label']")
    private WebElement checkboxBrand;*/

    private final String PAGE_URL = "https://www.lamoda.by/c/355/clothes-zhenskaya-odezhda/?sitelink=topmenuW&l=2";

    private By sortPriceAscendingButtonLocator = By.xpath("//li[@data-order='price_asc']");
    private By applyFilterButtonLocator = By.xpath("//span[@class='button button_s button_blue multifilter-actions__apply']");
    private By filterByBrandFieldLocator = By.xpath("//input[@class='text-field']");
    private By multiFilterItemLocator = By.xpath("//label[@class='multifilter-item__label']");
    private By brandsMultiFilterContainerLocator = By.xpath("//div[@class='multifilter multifilter_brands']//div[@class='dropdown']");

    public ClothesCategoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public ClothesCategoryPage openPage() {
        driver.get(PAGE_URL);
        /*new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(CustomConditions.waitForLoad());*/
        return this;
    }

    public ClothesCategoryPage openSortTypes() {
        /*new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(sortTypeButton)).click();*/
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", sortTypeButton);

        return this;
    }

    public ClothesCategoryPage sortByPriceAscending() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", sortPriceAscendingButton);
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(sortPriceAscendingButtonLocator))
                .click();
        return this;
    }

    public List<ProductItem> getItems() {
        return sortResultsList.findElements(By.xpath("/div"))
                .stream().map(item -> {
                    String title = item.findElement(By.className("products-list-item__type")).getText();
                    Matcher matcher = Pattern.compile("\\d+\\.\\d{2}").matcher(item.getText());
                    List<Double> itemPrices = new ArrayList<>();
                    while (matcher.find()) {
                        itemPrices.add(Double.valueOf(matcher.group()));
                    }
                    String brand = item.findElement(By.className("products-list-item__brand")).getText().trim();
                    return new ProductItem(title, itemPrices.stream().min(Double::compareTo).orElse(0.0), brand);
                })
                .collect(Collectors.toList());
    }

    public List<Double> getPrices() {
        return sortResultsList.findElements(By.xpath("/div"))
                .stream().map(item -> {
                    Matcher matcher = Pattern.compile("\\d+\\.\\d{2}").matcher(item.getText());
                    List<Double> itemPrices = new ArrayList<>();
                    while (matcher.find()) {
                        itemPrices.add(Double.valueOf(matcher.group()));
                    }
                    return itemPrices.stream().min(Double::compareTo).orElse(.0);
                }).collect(Collectors.toList());

    }

    public ClothesCategoryPage openFilterByPriceDetails() {
        /*new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(filterByPriceButton)).click();*/
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", filterByPriceButton);
        return this;
    }

    public ClothesCategoryPage inputMaxPrice(Integer price) {
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.visibilityOf(maxFilterRangeField)).sendKeys(price.toString());
//        maxFilterRangeField.sendKeys(price.toString());
        return this;
    }

    public ClothesCategoryPage clickApplyFilterByPriceButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", applyFilterByPriceButton);
//        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
//                .until(ExpectedConditions.elementToBeClickable(applyFilterByPriceButton)).click();
        logger.info("apply button clicked");
        return this;
    }

    public ClothesCategoryPage clickApplyFilterByBrandButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", applyFilterByBrandButton);
//        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
//                .until(ExpectedConditions.elementToBeClickable(applyFilterByBrandButton)).click();
        logger.info("apply button clicked");
        return this;
    }

    public ClothesCategoryPage openFilterByBrandDetails() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", filterByBrandButton);
        logger.info("filter by brands details opened");
        return this;
    }

    public ClothesCategoryPage inputBrand(String brand) {
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", brandsMultiFilterContainer.findElement(filterByBrandFieldLocator));
      new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.visibilityOf(filterByBrandField)).sendKeys(brand);
        logger.info("wrote keyword to brand input field");
        return this;
    }

    public ClothesCategoryPage selectBrandCheckbox() {
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", brandsMultiFilterContainer);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", brandsMultiFilterContainer.findElement(multiFilterItemLocator));
        logger.info("brand checkbox selected");
        /*new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(brandsMultiFilterContainer.findElement(multiFilterItemLocator))).click();*/
        return this;

    }
}
