package com.test.amaro.amarotest.activities.detailsActivity;

import android.content.Context;

/**
 * Created by rodri on 14/04/2018.
 */

public class DetailsActivityPresenter implements DetailsActivityContract.Presenter {
    private DetailsActivityContract.View mDetailsActivityView;
    private Context mContext;

    DetailsActivityPresenter(Context context, DetailsActivityContract.View mainActivityView) {
        this.mDetailsActivityView = mainActivityView;
        this.mContext = context;
        mainActivityView.setPresenter(this);
    }

    @Override
    public void start() {
        mDetailsActivityView.setToolbarImage();
        mDetailsActivityView.setProductsDetails();
    }
}
