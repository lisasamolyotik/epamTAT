package com.example.model;

public class ProductItem {
    private String title;
    private Double price;
    private String brand;

    public ProductItem(String title, Double price, String brand) {
        this.title = title;
        this.price = price;
        this.brand = brand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
