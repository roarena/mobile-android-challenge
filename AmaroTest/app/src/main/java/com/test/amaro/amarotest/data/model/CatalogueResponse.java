package com.test.amaro.amarotest.data.model;

import java.util.List;

public class CatalogueResponse {

    private List<ProductsItem> products;

    public void setProducts(List<ProductsItem> products) {
        this.products = products;
    }

    public List<ProductsItem> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return
                "CatalogueResponse{" +
                        "products = '" + products + '\'' +
                        "}";
    }
}