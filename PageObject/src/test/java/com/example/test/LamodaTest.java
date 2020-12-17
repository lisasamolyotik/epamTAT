package com.example.test;

import com.example.page.ClothesCategoryPage;
import com.example.page.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.IntStream;

public class LamodaTest {

    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testSearchResultContainsEnteredSearchKey() {
        final String searchKey = "куртка";

        boolean resultsContainSearchKey = new HomePage(driver)
                .openPage()
                .inputSearchKey(searchKey)
                .search()
                .getItemsTitles()
                .stream().anyMatch(title -> title.toLowerCase().contains(searchKey));

        Assert.assertTrue(resultsContainSearchKey);
    }

    @Test
    public void testItemsAreSortedByPrice() {
        List<Double> prices = new ClothesCategoryPage(driver)
                .openPage()
                .openSortTypes()
                .sortByPriceAscending()
                .getPrices();

        boolean itemsAreSortedByPriceInAscendingOrder = IntStream
                .range(0, prices.size() - 1).noneMatch(i -> prices.get(i) > prices.get(i + 1));

        Assert.assertTrue(itemsAreSortedByPriceInAscendingOrder);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

}
