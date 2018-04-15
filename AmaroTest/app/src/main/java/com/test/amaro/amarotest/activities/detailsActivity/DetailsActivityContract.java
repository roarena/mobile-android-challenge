package com.test.amaro.amarotest.activities.detailsActivity;

import com.test.amaro.amarotest.activities.BasePresenter;
import com.test.amaro.amarotest.activities.BaseView;

/**
 * Created by rodri on 14/04/2018.
 */

public interface DetailsActivityContract {
    interface View extends BaseView<Presenter> {
        void setToolbarImage();

        void setProductsDetails();
    }

    interface Presenter extends BasePresenter {

    }
}
