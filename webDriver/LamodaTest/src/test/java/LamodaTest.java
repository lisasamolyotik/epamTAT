import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class LamodaTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void testSearchResultContainsEnteredSearchKey() {
        driver.get("https://www.lamoda.by/");
        final String searchKey = "куртка";
        final String searchFieldPath = "//input[@placeholder='Поиск']";
        final String searchButtonPath = "//div[@class='button button_blue search__button js-search-button']";
        final String searchResultItemTypePath = "//div[@class='products-catalog__list']//span[@class='products-list-item__type']";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(searchFieldPath))).sendKeys(searchKey);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchButtonPath))).click();

        List<String> titles = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath(searchResultItemTypePath)))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(titles.stream().anyMatch(title -> title.toLowerCase().contains(searchKey)));
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
