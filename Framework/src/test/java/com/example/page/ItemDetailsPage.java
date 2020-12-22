package com.example.page;

import com.example.model.ProductItem;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemDetailsPage extends AbstractPage {

    private final String PAGE_URL = "https://www.lamoda.by/p/ch587dweydq2/accs-christiandior-ochki-solntsezaschitnye/";

    @FindBy(xpath = "//div[@class='ii-product__aside-wrapper']//button")
    private WebElement addToCartButton;

    @FindBy(xpath = "//div[@class='logo-line-wrapper width-wrapper']//div[@class='vue-widget']//a[2]")
    private WebElement goToCartButton;

    @FindBy(xpath = "//a[@class='product-title__brand-name']")
    private WebElement itemBrandName;

    @FindBy(xpath = "//span[@class='product-title__model-name']")
    private WebElement itemTitle;

    @FindBy(xpath = "//span[@class='_1xktn17sNuFwy45DZmZ5Oe product-prices__price_current']/span")
    private WebElement itemPrice;

    private By goToCartButtonLocator = By.xpath("//div[@class='logo-line-wrapper width-wrapper']//div[@class='vue-widget']//a[2]");
    private By addToCartButtonLocator = By.xpath("//div[@class='ii-product__aside-wrapper']//button");

    public ItemDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public ItemDetailsPage openPage() {
        driver.get(PAGE_URL);
        return this;
    }

    public ItemDetailsPage addToCart() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", addToCartButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", addToCartButton);
        return this;
    }

    public CartPage goToCart() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", goToCartButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", goToCartButton);
        return new CartPage(driver);
    }

    public ProductItem getItem() {
        String title = itemTitle.getText();
        String brand = itemBrandName.getText();
        Matcher matcher = Pattern.compile("\\d+\\s\\d+\\.\\d{2}").matcher(itemPrice.getText());
        Double price = 0d;
        if (matcher.find()) {
            price = Double.valueOf(matcher.group().replaceAll("\\s+", ""));
        }

        return new ProductItem(title, price, brand);
    }


}
