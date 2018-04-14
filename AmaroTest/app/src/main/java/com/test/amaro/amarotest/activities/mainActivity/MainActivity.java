package com.test.amaro.amarotest.activities.mainActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.amaro.amarotest.R;
import com.test.amaro.amarotest.data.model.ProductsItem;
import com.test.amaro.amarotest.utils.C;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private MainActivityContract.Presenter mMainActivityPresenter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CatalogueAdapter mCatalogueAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_products_count)
    TextView mTvProductsCount;

    @BindView(R.id.pb_main)
    ProgressBar mPbLoading;

    @BindView(R.id.tv_main_try_again)
    TextView mTvTryAgain;

    @BindView(R.id.btn_main_try_again)
    Button mBtnTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mCatalogueAdapter = new CatalogueAdapter(getBaseContext(), listItemClickListener);
        mLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCatalogueAdapter);

        mMainActivityPresenter = new MainActivityPresenter(getBaseContext(), this);

        mBtnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivityPresenter.loadProducts();
            }
        });
    }

    private CatalogueAdapter.ListItemClickListener listItemClickListener = new CatalogueAdapter.ListItemClickListener() {
        @Override
        public void onListItemClick(ProductsItem product) {
            Log.d(C.LOG_TAG, product.getName());
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mMainActivityPresenter.start();
    }

    @Override
    public void setPresenter(MainActivityContract.Presenter presenter) {
        if (presenter == null) {
            throw new NullPointerException("Presenter is null");
        }
        mMainActivityPresenter = presenter;
    }

    @Override
    public void changeLoadingStatus(int status) {
        mPbLoading.setVisibility(status);
    }

    @Override
    public void toggleTryAgain(int status) {
        mTvTryAgain.setVisibility(status);
        mBtnTryAgain.setVisibility(status);
    }

    @Override
    public void showProducts(List<ProductsItem> productsItemList) {
        mCatalogueAdapter.replaceData(productsItemList);
    }

    @Override
    public void setSubtitle(String subtitle) {
        mTvProductsCount.setText(subtitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }
}
