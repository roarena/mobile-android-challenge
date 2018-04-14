package com.test.amaro.amarotest.data.model;

public class SizesItem {

    private String size;
    private boolean available;
    private String sku;

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSku() {
        return sku;
    }

    @Override
    public String toString() {
        return
                "SizesItem{" +
                        "size = '" + size + '\'' +
                        ",available = '" + available + '\'' +
                        ",sku = '" + sku + '\'' +
                        "}";
    }
}