package com.test.amaro.amarotest.activities.mainActivity;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.test.amaro.amarotest.R;
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
    private Context mContext;

    MainActivityPresenter(Context context, MainActivityContract.View mainActivityView) {
        this.mMainActivityView = mainActivityView;
        this.mContext = context;
        mainActivityView.setPresenter(this);
    }

    @Override
    public void start() {
        loadProducts();
    }

    @Override
    public void onResponseOK(List<ProductsItem> productsItemList) {
        mMainActivityView.showProducts(productsItemList);
        mMainActivityView.changeLoadingStatus(View.GONE);
        mMainActivityView.setSubtitle(String.format(mContext.getString(R.string.products_count), String.valueOf(productsItemList.size())));
    }

    @Override
    public void onResponseFail(Throwable t) {
        Log.e(C.LOG_TAG, t.getMessage());

        mMainActivityView.toggleTryAgain(View.VISIBLE);
        mMainActivityView.changeLoadingStatus(View.GONE);
        mMainActivityView.setSubtitle(mContext.getString(R.string.error));
    }

    @Override
    public void loadProducts() {
        mMainActivityView.changeLoadingStatus(View.VISIBLE);
        mMainActivityView.toggleTryAgain(View.GONE);
        mMainActivityView.setSubtitle(mContext.getString(R.string.loading));
        mCatalogueController = new CatalogueController();
        mCatalogueController.start(this);
    }
}
