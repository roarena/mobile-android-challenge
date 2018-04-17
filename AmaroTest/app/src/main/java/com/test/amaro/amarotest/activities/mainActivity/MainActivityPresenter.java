package com.test.amaro.amarotest.activities.mainActivity;

import android.util.Log;
import android.view.View;

import com.test.amaro.amarotest.R;
import com.test.amaro.amarotest.data.model.ProductsItem;
import com.test.amaro.amarotest.data.network.CatalogueController;
import com.test.amaro.amarotest.data.network.interfaces.UiController;
import com.test.amaro.amarotest.data.repository.ProductsCache;
import com.test.amaro.amarotest.utils.C;

import java.util.List;

/**
 * Created by rodri on 13/04/2018.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter, UiController {

    private CatalogueController mCatalogueController;
    private MainActivityContract.View mMainActivityView;

    MainActivityPresenter(MainActivityContract.View mainActivityView) {
        this.mMainActivityView = mainActivityView;
        mainActivityView.setPresenter(this);
    }

    @Override
    public void start() {
        loadProducts();
    }

    @Override
    public void onResponseOK(List<ProductsItem> productsItemList) {
        ProductsCache.getInstance().setOriginalList(productsItemList);
        mMainActivityView.showProducts(productsItemList, C.SORT_ORIGINAL);
        mMainActivityView.changeLoadingStatus(View.GONE);
        mMainActivityView.enableUiFilters();
    }

    @Override
    public void onResponseFail(Throwable t) {
        Log.e(C.LOG_TAG, t.getMessage());
        mMainActivityView.toggleTryAgain(View.VISIBLE);
        mMainActivityView.changeLoadingStatus(View.GONE);
    }

    @Override
    public void loadProducts() {
        mMainActivityView.changeLoadingStatus(View.VISIBLE);
        mMainActivityView.toggleTryAgain(View.GONE);
        mCatalogueController = new CatalogueController();
        mCatalogueController.start(this);
    }

    @Override
    public void onListClick(ProductsItem productsItem, View cardImage) {
        mMainActivityView.showProductDetail(productsItem, cardImage);
    }

    @Override
    public void onShowFiltersClick(int status) {
        if (status == View.VISIBLE) {
            mMainActivityView.toggleFilterUi(View.GONE);
        } else {
            mMainActivityView.toggleFilterUi(View.VISIBLE);
        }
    }

    @Override
    public void onSaleFilterClick(boolean status, List<ProductsItem> productsItems) {
        if (status) {
            mMainActivityView.showFilteredProducts(ProductsCache.getInstance().
                    retrieveList(C.SORT_SALE, productsItems));
        } else {
            mMainActivityView.showFilteredProducts(
                    ProductsCache.getInstance().getOriginalList());
        }
    }

    @Override
    public void onFilterChangeClick(int sortType, List<ProductsItem> productsItems) {
        switch (sortType) {
            case R.id.rb_filter_higher:
                mMainActivityView.showFilteredProducts(ProductsCache.getInstance().
                        retrieveList(C.SORT_HIGHER_FIRST, productsItems));
                break;
            case R.id.rb_filter_lower:
                mMainActivityView.showFilteredProducts(ProductsCache.getInstance().
                        retrieveList(C.SORT_LOWER_FIRST, productsItems));
                break;
            default:
                mMainActivityView.showFilteredProducts(
                        ProductsCache.getInstance().getOriginalList());
        }
    }

    @Override
    public void onClearFilterClicked() {
        mMainActivityView.showFilteredProducts(ProductsCache.getInstance().
                retrieveList(C.SORT_ORIGINAL, ProductsCache.getInstance().getOriginalList()));
        mMainActivityView.updateFiltersUi();
    }
}
