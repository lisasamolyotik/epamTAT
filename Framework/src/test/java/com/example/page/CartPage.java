package com.example.page;

import com.example.model.ProductItem;
import com.example.utils.PriceMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends AbstractPage {

    private final String PAGE_URL = "https://www.lamoda.by/checkout/cart/#old";

    @FindBy(xpath = "//div[@id='checkout__cart']")
    private WebElement checkoutCart;

//    @FindBy(xpath = "//div[@class='package-block__error js-package-error']")
//    private WebElement couponErrorMessage;

    private By cartItemTitleLocator = By.xpath("//div[@class='cpi__name']");
    private By cartItemBrandLocator = By.xpath("//div[@class='cpi__name']/b");
    private By cartItemPriceLocator = By.xpath("//div[@class='cpi__price-final']");

    private By couponInputFieldLocator = By.id("id_coupon_code");
    private By couponApplyButtonLocator = By.xpath("//div[@class='checkout-coupon-wrapper']//button");
    private By couponErrorMessageLocator = By.xpath("//div[@class='package-block__error js-package-error']");

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public CartPage openPage() {
        driver.get(PAGE_URL);
        return this;
    }

    public ProductItem getItemFromCart() {
        scrollTo(checkoutCart);
        String brand = checkoutCart.findElement(cartItemBrandLocator).getText();
        String title = checkoutCart.findElement(cartItemTitleLocator).getText().replace(brand, "").trim();
        Double price = PriceMatcher.getPriceFromWebElement(checkoutCart.findElement(cartItemPriceLocator));
        return new ProductItem(title, price, brand);
    }

    public CartPage inputCoupon(String coupon) {
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(couponInputFieldLocator))
                .sendKeys(coupon);
        logger.info("coupon is inputted");
        return this;
    }

    public CartPage applyCoupon() {
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(couponApplyButtonLocator))
                .click();
        logger.info("coupon is applied");
        return this;
    }

    public Double getCartPrice() {
        Double price = PriceMatcher.getPriceFromWebElement(checkoutCart.findElement(cartItemPriceLocator));
        logger.info("price from cart - " + price);
        return price;
    }

    public boolean isErrorMessageVisible() {
        new WebDriverWait(driver, WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(couponErrorMessageLocator));
        logger.info("error message is visible");
        return true;
    }
}
