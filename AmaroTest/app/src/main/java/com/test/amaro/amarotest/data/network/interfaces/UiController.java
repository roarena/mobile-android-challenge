package com.test.amaro.amarotest.data.network.interfaces;

import com.test.amaro.amarotest.data.model.ProductsItem;

import java.util.List;

/**
 * Created by rodri on 13/04/2018.
 */

public interface UiController {
    void onResponseOK(List<ProductsItem> productsItemList);

    void onResponseFail(Throwable t);
}
