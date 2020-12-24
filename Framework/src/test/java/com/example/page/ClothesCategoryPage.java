package com.example.page;

import com.example.model.ProductItem;
import com.example.wait.CustomConditions;
import org.openqa.selenium.By;
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

    double defaultPriceValue = 0.0;

    private final String PAGE_URL = "https://www.lamoda.by/c/355/clothes-zhenskaya-odezhda/?sitelink=topmenuW&l=2";

    private String priceFilterPath = "//div[@class='multifilter multifilter_price']";
    private String brandFilterPath = "//div[@class='multifilter multifilter_brands']";

    private String filterButtonPath = "/div/span";
    private String applyFilterButtonPath = "//div[@class='multifilter-actions']//span";

    @FindAll(@FindBy(xpath = "//*[@class='products-catalog__list']"))
    private WebElement sortResultsList;

    @FindBy(xpath = "//div[@class='multifilter multifilter_brands']//div[@class='dropdown']")
    private WebElement brandsMultiFilterContainer;

    private By lowerBoundPriceFieldLocator = By.xpath(priceFilterPath + "//input[@class='text-field range__value range__value_left']");
    private By upperBoundPriceFieldLocator = By.xpath(priceFilterPath + "//input[@class='text-field range__value range__value_right']");

    private By sortPriceAscendingButtonLocator = By.xpath("//li[@data-order='price_asc']");

    private By applyFilterByBrandButtonLocator = By.xpath(brandFilterPath + applyFilterButtonPath);
    private By applyFilterByPriceButtonLocator = By.xpath(priceFilterPath + applyFilterButtonPath);

    private By filterByBrandFieldLocator = By.xpath(brandFilterPath + "//input[@class='text-field']");
    private By multiFilterItemLocator = By.xpath("//label[@class='multifilter-item__label']");

    private By filterByBrandButtonLocator = By.xpath(brandFilterPath + filterButtonPath);
    private By filterByPriceButtonLocator = By.xpath(priceFilterPath + filterButtonPath);

    private By productDescriptionLocator = By.className("products-list-item__type");
    private By productBrandLocator = By.className("products-list-item__brand");

    private By sortResultListItemLocator = By.xpath("//*[@class='products-catalog__list']/div");
    private By catalogContainerLocator = By.xpath("//div[@class='product-catalog-main']");

    public ClothesCategoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public ClothesCategoryPage openPage() {
        driver.get(PAGE_URL);
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(CustomConditions.waitForLoad());
        return this;
    }

    public ClothesCategoryPage openSortTypes() {
        click(driver.findElement(filterByPriceButtonLocator));
        return this;
    }

    public ClothesCategoryPage sortByPriceAscending() {
        click(driver.findElement(sortPriceAscendingButtonLocator));
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(catalogContainerLocator));
        return this;
    }

    public List<ProductItem> getItems() {
        return sortResultsList.findElements(By.xpath("/div"))
                .stream().map(item -> {
                    String title = item.findElement(productDescriptionLocator).getText();
                    Matcher matcher = Pattern.compile("\\d+\\.\\d{2}").matcher(item.getText());
                    List<Double> itemPrices = new ArrayList<>();
                    while (matcher.find()) {
                        itemPrices.add(Double.valueOf(matcher.group()));
                    }
                    Double price = itemPrices.stream().min(Double::compareTo).orElse(defaultPriceValue);
                    String brand = item.findElement(productBrandLocator).getText().trim();
                    return new ProductItem(title, price, brand);
                })
                .collect(Collectors.toList());
    }

    public ClothesCategoryPage openFilterByPriceDetails() {
        click(driver.findElement(filterByPriceButtonLocator));
        logger.info("opened price filter details");
        return this;
    }

    public ClothesCategoryPage openFilterByBrandDetails() {
        click(driver.findElement(filterByBrandButtonLocator));
        logger.info("opened brand filter details");
        return this;
    }

    public ClothesCategoryPage inputMaxPrice(Integer price) {
        write(brandsMultiFilterContainer.findElement(upperBoundPriceFieldLocator), price.toString());
        logger.info("wrote max price bound - " + price);
        return this;
    }

    public ClothesCategoryPage clickApplyFilterByPriceButton() {
        click(driver.findElement(applyFilterByPriceButtonLocator));
        logger.info("apply button clicked");
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(catalogContainerLocator));
        return this;
    }

    public ClothesCategoryPage clickApplyFilterByBrandButton() {
        click(driver.findElement(applyFilterByBrandButtonLocator));
        logger.info("apply button clicked");
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(catalogContainerLocator));
        return this;
    }

    public ClothesCategoryPage inputBrand(String brand) {
        write(brandsMultiFilterContainer.findElement(filterByBrandFieldLocator), brand);
        logger.info("wrote keyword to brand input field");
        return this;
    }

    public ClothesCategoryPage selectBrandCheckbox() {
        click(driver.findElement(multiFilterItemLocator));
        logger.info("brand checkbox selected");
        return this;
    }
}
