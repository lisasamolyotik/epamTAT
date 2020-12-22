package com.example.test;

import com.example.model.ProductItem;
import com.example.page.ClothesCategoryPage;
import com.example.page.HomePage;
import com.example.page.ItemDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.IntStream;

public class LamodaTest extends CommonConditions {

   /* @Test
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
        List<ProductItem> items = new ClothesCategoryPage(driver)
                .openPage()
                .openSortTypes()
                .sortByPriceAscending()
                .getItems();


        boolean itemsAreSortedByPriceInAscendingOrder = IntStream
                .range(0, items.size() - 1)
                .noneMatch(i -> items.get(i).getPrice() > items.get(i + 1).getPrice());

        Assert.assertTrue(itemsAreSortedByPriceInAscendingOrder);
    }*/

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

        boolean pricesAreLessThanMaxBound = items.stream()
                .noneMatch(item -> item.getPrice() > maxFilterRangeBound);

        boolean brandsMatchTheFilterValue = items.stream()
                .allMatch(item -> item.getBrand().toLowerCase().equals(brandFilterValue.toLowerCase()));

        Assert.assertTrue(pricesAreLessThanMaxBound);
        Assert.assertTrue(brandsMatchTheFilterValue);
    }

   /* @Test
    public void testItemIsAddedToCart() {
        ItemDetailsPage itemDetailsPage = new ItemDetailsPage(driver)
                .openPage();

        ProductItem initialItem = itemDetailsPage.getItem();

        ProductItem itemFromCart = itemDetailsPage
                .addToCart()
                .goToCart()
                .getItemFromCart();

        boolean itemTitleFromCartMatchesToInitialItem = initialItem.getTitle().toLowerCase().contains(itemFromCart.getTitle().toLowerCase());
        boolean itemBrandFromCartMatchesToInitialItem = initialItem.getBrand().toLowerCase().equals(itemFromCart.getBrand().toLowerCase());
        boolean itemPriceFromCartMatchesToInitialItem = initialItem.getPrice().equals(itemFromCart.getPrice());

        Assert.assertTrue(itemTitleFromCartMatchesToInitialItem);
        Assert.assertTrue(itemBrandFromCartMatchesToInitialItem);
        Assert.assertTrue(itemPriceFromCartMatchesToInitialItem);
    }*/
}
