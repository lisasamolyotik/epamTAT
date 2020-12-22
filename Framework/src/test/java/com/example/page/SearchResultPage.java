package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultPage extends AbstractPage {

    @FindAll(@FindBy(xpath = "//div[@class='products-catalog__list']//span[@class='products-list-item__type']"))
    private List<WebElement> searchResults;

    public SearchResultPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    protected AbstractPage openPage() {
        return null;
    }

    public List<String> getItemsTitles() {
        return searchResults.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
