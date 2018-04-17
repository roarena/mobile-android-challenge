package com.test.amaro.amarotest.data.repository;

import com.test.amaro.amarotest.data.model.ProductsItem;
import com.test.amaro.amarotest.utils.C;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by rodri on 15/04/2018.
 */

public class ProductsCache {
    private static ProductsCache instance = null;
    private List<ProductsItem> mOriginalList;
    private int lastSortType = C.SORT_ORIGINAL;

    protected ProductsCache() {
        // Exists only to defeat instantiation.
    }

    public static ProductsCache getInstance() {
        if (instance == null) {
            instance = new ProductsCache();
        }
        return instance;
    }

    public void setOriginalList(List<ProductsItem> mOriginalList) {
        this.mOriginalList = mOriginalList;
    }

    public List<ProductsItem> getOriginalList() {
        filterListByPrice(mOriginalList, lastSortType);
        return mOriginalList;
    }

    public List<ProductsItem> retrieveList(int sortType, List<ProductsItem> productsItems) {
        switch (sortType) {
            default:
                return mOriginalList;
            case C.SORT_SALE:
                return getOnSaleList(productsItems);
            case C.SORT_HIGHER_FIRST:
                lastSortType = C.SORT_HIGHER_FIRST;
                filterListByPrice(productsItems, C.SORT_HIGHER_FIRST);
                return productsItems;
            case C.SORT_LOWER_FIRST:
                lastSortType = C.SORT_LOWER_FIRST;
                filterListByPrice(productsItems, C.SORT_LOWER_FIRST);
                return productsItems;
        }
    }

    private void filterListByPrice(List<ProductsItem> productsItems, final int sortType) {
        Collections.sort(productsItems, new Comparator<ProductsItem>() {
            public int compare(ProductsItem c1, ProductsItem c2) {
                if (sortType == C.SORT_HIGHER_FIRST) {
                    return Double.compare(
                            getPriceFromJson(c2.getActual_price()),
                            getPriceFromJson(c1.getActual_price()));
                } else {
                    return Double.compare(
                            getPriceFromJson(c1.getActual_price()),
                            getPriceFromJson(c2.getActual_price()));
                }
            }
        });
    }

    private double getPriceFromJson(String price) {
        return Double.valueOf(price.replaceAll("\\D+", ""));
    }

    private List<ProductsItem> getOnSaleList(List<ProductsItem> productsItems) {
        List<ProductsItem> onSaleList = new ArrayList<>();
        for (ProductsItem product : productsItems) {
            if (product.isOn_sale()) {
                onSaleList.add(product);
            }
        }
        return onSaleList;
    }
}
