package com.test.amaro.amarotest.activities.mainActivity;

import android.util.Log;

import com.test.amaro.amarotest.data.model.CatalogueResponse;
import com.test.amaro.amarotest.data.model.ProductsItem;
import com.test.amaro.amarotest.data.network.CatalogueController;
import com.test.amaro.amarotest.data.network.interfaces.UiController;
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
        mCatalogueController = new CatalogueController();
        mCatalogueController.start(this);
    }

    @Override
    public void onResponseOK(List<ProductsItem> productsItemList) {
        Log.i(C.LOG_TAG,productsItemList.get(0).getName());
        mMainActivityView.showProducts(productsItemList);
    }

    @Override
    public void onResponseFail(Throwable t) {
        Log.e(C.LOG_TAG, t.getMessage());
    }
}
