package com.example.page;

import com.example.model.ProductItem;
import com.example.wait.CustomConditions;
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

public class CartPage extends AbstractPage{

    private final String PAGE_URL = "https://www.lamoda.by/checkout/cart/#old";

    /*@FindBy(xpath = "//div[@class='cpi__name']")
    private WebElement */

    @FindBy(xpath = "//div[@id='checkout__cart']")
    private WebElement checkoutCart;

    private By cartItemTitleLocator = By.xpath("//div[@class='cpi__name']");
    private By cartItemBrandLocator = By.xpath("//div[@class='cpi__name']/b");
    private By cartItemPriceLocator = By.xpath("//div[@class='cpi__price-final']");

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public AbstractPage openPage() {
        driver.get(PAGE_URL);
        return this;
    }

    public ProductItem getItemFromCart() {
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(CustomConditions.waitForLoad());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", checkoutCart);
        String brand = checkoutCart.findElement(cartItemBrandLocator).getText();
        String title = checkoutCart.findElement(cartItemTitleLocator).getText().replace(brand, "");
        Matcher matcher = Pattern.compile("\\d+\\s\\d+\\.\\d{2}").matcher(checkoutCart.findElement(cartItemPriceLocator).getText());
        Double price = 0d;
        if (matcher.find()) {
            price = Double.valueOf(matcher.group().replaceAll("\\s+", ""));
        }
        return new ProductItem(title, price, brand);
    }
}
