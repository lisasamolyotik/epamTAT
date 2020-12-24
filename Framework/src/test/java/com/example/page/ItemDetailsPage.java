package com.example.page;

import com.example.model.ProductItem;
import com.example.utils.PriceMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ItemDetailsPage extends AbstractPage {

    private final ProductItem productItem;

    @FindBy(xpath = "//a[@class='product-title__brand-name']")
    private WebElement itemBrandName;

    @FindBy(xpath = "//span[@class='product-title__model-name']")
    private WebElement itemTitle;

    @FindBy(xpath = "//span[@class='_1xktn17sNuFwy45DZmZ5Oe product-prices__price_current']/span")
    private WebElement itemPrice;

    private By goToCartButtonLocator = By.xpath("//div[@class='logo-line-wrapper width-wrapper']//div[@class='vue-widget']//a[2]");
    private By addToCartButtonLocator = By.xpath("//div[@class='ii-product__aside-wrapper']//button");

    private By itemPriceLocator = By.xpath("//div[@class='ii-product-buy']//*[@itemprop='offers']");

    public ItemDetailsPage(WebDriver driver, ProductItem item) {
        super(driver);
        productItem = item;
        PageFactory.initElements(this.driver, this);
    }

    @Override
    public ItemDetailsPage openPage() {
        driver.get(productItem.getUrl());
        return this;
    }

    public ItemDetailsPage addToCart() {
        click(driver.findElement(addToCartButtonLocator));
        logger.info("added item to cart");
        return this;
    }

    public CartPage goToCart() {
        click(driver.findElement(goToCartButtonLocator));
        logger.info("went to cart");
        return new CartPage(driver);
    }

    public ProductItem getItem() {
        String title = itemTitle.getText();
        String brand = itemBrandName.getText();
        Double price = PriceMatcher.getPriceFromWebElement(driver.findElement(itemPriceLocator));
        return new ProductItem(title, price, brand);
    }


}
