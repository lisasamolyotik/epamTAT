import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LamodaTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void testSearchResultContainsEnteredSearchKey() {
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://www.lamoda.by/");
        final String searchKey = "куртка";
        final String searchFieldPath = "//*[@id='menu-wrapper']//input[@class='text-field text-field_large search__input js-search-field']";
        final String searchButtonPath = "//div[@class='button button_blue search__button js-search-button']";
        final String searchResultItemTypePath = "//div[@class='products-catalog__list']//span[@class='products-list-item__type']";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(searchFieldPath))).sendKeys(searchKey);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchButtonPath))).click();

        List<String> titles = wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.xpath(searchResultItemTypePath)))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(titles.stream().anyMatch(title -> title.toLowerCase().contains(searchKey)));
    }

    @Test
    public void testSortByPrice() {
        driver.manage().window().maximize();
        final String sortTypeButtonPath = "//span[@class='products-catalog__sort']//span[@class='button button_right button_wo-pdng-r']";
        final String sortPriceAscendingButtonPath = "//li[@data-order='price_asc']";
        final String sortResultElementPath = "//div[@class='products-list-item']";

        driver.get("https://www.lamoda.by/c/355/clothes-zhenskaya-odezhda/?sitelink=topmenuW&l=2");

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath(sortTypeButtonPath)));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(sortTypeButtonPath))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(sortPriceAscendingButtonPath))).click();

        List<Double> prices = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(sortResultElementPath)))
                .stream().map(item -> {
                    Matcher matcher = Pattern.compile("\\d+\\.\\d{2}").matcher(item.getText());
                    List<Double> itemPrices = new ArrayList<>();
                    while(matcher.find()) {
                        itemPrices.add(Double.valueOf(matcher.group()));
                    }
                    return itemPrices.stream().min(Double::compareTo).orElse(.0);
                }).collect(Collectors.toList());

        Assert.assertTrue(IntStream.range(0, prices.size() - 1).noneMatch(i -> prices.get(i) > prices.get(i+1)));
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
