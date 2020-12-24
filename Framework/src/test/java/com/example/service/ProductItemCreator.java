package com.example.service;

import com.example.model.ProductItem;

public class ProductItemCreator {

    private static String TITLE = "Очки солнцезащитные";
    private static Double PRICE = 1119.00;
    private static String BRAND = "Christian Dior";
    private static String URL = "https://www.lamoda.by/p/ch587dweydq2/accs-christiandior-ochki-solntsezaschitnye/";

    public static ProductItem withCredentials() {
        return new ProductItem(TITLE, PRICE, BRAND, URL);
    }
}
