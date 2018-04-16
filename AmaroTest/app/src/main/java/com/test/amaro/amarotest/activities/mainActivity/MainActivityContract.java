package com.test.amaro.amarotest.activities.mainActivity;

import com.test.amaro.amarotest.activities.BasePresenter;
import com.test.amaro.amarotest.activities.BaseView;
import com.test.amaro.amarotest.data.model.CatalogueResponse;
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

        void toggleFilterUi(int status);

        void onSaleClick();

        void filterClick(int filterClicked);

        void clearFilterClicked();

        void enableUiFilters();
    }

    interface Presenter extends BasePresenter {
        void loadProducts();

        void onFilterClick(int status);

        void filterChange(int sortType);
    }
}
