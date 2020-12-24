package com.example.test;

import com.example.model.ProductItem;
import com.example.page.CartPage;
import com.example.page.ClothesCategoryPage;
import com.example.page.HomePage;
import com.example.page.ItemDetailsPage;
import com.example.service.ProductItemCreator;
import com.example.type.CountryType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LamodaTest extends CommonConditions {

    @Test
    public void testSearchResultContainsEnteredSearchKey() {
        final String searchKey = "куртка";

        boolean resultsContainSearchKey = new HomePage(driver)
                .openPage()
                .inputSearchKey(searchKey)
                .search()
                .getItemsTitles()
                .stream().map(String::toLowerCase)
                .collect(Collectors.toList())
                .contains(searchKey);

        Assert.assertTrue(resultsContainSearchKey);
    }

    @Test
    public void testItemsAreSortedByPrice() {
        List<ProductItem> items = new ClothesCategoryPage(driver)
                .openPage()
                .openSortTypes()
                .sortByPriceAscending()
                .getItems();


        boolean itemsAreSortedByPriceInAscendingOrder = IntStream
                .range(0, items.size() - 1)
                .noneMatch(i -> items.get(i).getPrice() > items.get(i + 1).getPrice());

        Assert.assertTrue(itemsAreSortedByPriceInAscendingOrder);
    }

    @Test
    public void testItemsAreFiltered() {
        final Integer maxFilterRangeBound = 50;
        final String brandFilterValue = "adidas";

        List<ProductItem> items = new ClothesCategoryPage(driver)
                .openPage()
                .openFilterByPriceDetails()
                .inputMaxPrice(maxFilterRangeBound)
                .clickApplyFilterByPriceButton()
                .openFilterByBrandDetails()
                .inputBrand(brandFilterValue)
                .selectBrandCheckbox()
                .clickApplyFilterByBrandButton()
                .getItems();

        // TODO:
        boolean pricesAreLessThanMaxBound = items.stream()
                .allMatch(item -> item.getPrice() < maxFilterRangeBound);

        // TODO:
        boolean brandsMatchTheFilterValue = items.stream()
                .allMatch(item -> item.getBrand().toLowerCase().equals(brandFilterValue.toLowerCase()));

        Assert.assertTrue(pricesAreLessThanMaxBound);
        Assert.assertTrue(brandsMatchTheFilterValue);
    }

    @Test
    public void testItemIsAddedToCart() {
        ProductItem initialItem = ProductItemCreator.withCredentials();

        ProductItem itemFromCart = new ItemDetailsPage(driver, initialItem)
                .openPage()
                .addToCart()
                .goToCart()
                .getItemFromCart();

        boolean itemTitleFromCartMatchesToInitialItem = initialItem.getTitle().equals(itemFromCart.getTitle());
        boolean itemBrandFromCartMatchesToInitialItem = initialItem.getBrand().equals(itemFromCart.getBrand());
        boolean itemPriceFromCartMatchesToInitialItem = initialItem.getPrice().equals(itemFromCart.getPrice());

        Assert.assertTrue(itemTitleFromCartMatchesToInitialItem);
        Assert.assertTrue(itemBrandFromCartMatchesToInitialItem);
        Assert.assertTrue(itemPriceFromCartMatchesToInitialItem);
    }

    @Test
    public void testPromoIsNotApplied() {
        ProductItem initialItem = ProductItemCreator.withCredentials();

        CartPage cartPage = new ItemDetailsPage(driver, initialItem)
                .openPage()
                .addToCart()
                .goToCart()
                .inputCoupon("coupon")
                .applyCoupon();

        boolean isPriceDidNotChanged = cartPage
                .getCartPrice()
                .equals(initialItem.getPrice());

        boolean isErrorMessageVisible = cartPage
                .isErrorMessageVisible();

        Assert.assertTrue(isErrorMessageVisible);
        Assert.assertTrue(isPriceDidNotChanged);
    }

    @Test
    public void testCountryIsChanged() {
        String expectedResult = "https://www.lamoda.kz/";

        boolean isCountryChanged = new HomePage(driver)
                .openPage()
                .openChangeCountryDetails()
                .selectCountry(CountryType.KAZAKHSTAN)
                .getCurrentUrl()
                .equals(expectedResult);

        Assert.assertTrue(isCountryChanged);
    }
}
