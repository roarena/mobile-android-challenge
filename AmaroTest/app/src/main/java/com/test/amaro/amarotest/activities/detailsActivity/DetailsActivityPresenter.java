package com.test.amaro.amarotest.activities.detailsActivity;

/**
 * Created by rodri on 14/04/2018.
 */

public class DetailsActivityPresenter implements DetailsActivityContract.Presenter {
    private DetailsActivityContract.View mDetailsActivityView;

    DetailsActivityPresenter(DetailsActivityContract.View mainActivityView) {
        this.mDetailsActivityView = mainActivityView;
        mainActivityView.setPresenter(this);
    }

    @Override
    public void start() {
        mDetailsActivityView.setToolbarImage();
        mDetailsActivityView.setProductsDetails();
    }
}
