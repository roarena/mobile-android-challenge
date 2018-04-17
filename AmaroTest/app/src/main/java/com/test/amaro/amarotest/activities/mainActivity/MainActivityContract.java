package com.test.amaro.amarotest.activities.mainActivity;


import com.test.amaro.amarotest.activities.BasePresenter;
import com.test.amaro.amarotest.activities.BaseView;
import com.test.amaro.amarotest.data.model.ProductsItem;

import java.util.List;

/**
 * Created by rodri on 13/04/2018.
 */

public interface MainActivityContract {
    interface View extends BaseView<Presenter> {
        void changeLoadingStatus(int status);

        void toggleTryAgain(int status);

        void showProducts(List<ProductsItem> productsItemList, int sortType);

        void showProductDetail(ProductsItem productsItem, android.view.View cardImage);

        void showFilteredProducts(List<ProductsItem> productsItems);

        void toggleFilterUi(int status);

        void filterClick(int filterClicked);

        void updateFiltersUi();

        void enableUiFilters();
    }

    interface Presenter extends BasePresenter {
        void loadProducts();

        void onListClick(ProductsItem productsItem, android.view.View cardImage);

        void onShowFiltersClick(int status);

        void onSaleFilterClick(boolean status, List<ProductsItem> productsItems);

        void onFilterChangeClick(int sortType, List<ProductsItem> productsItems);

        void onClearFilterClicked();
    }
}
