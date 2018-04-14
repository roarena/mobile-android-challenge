package com.test.amaro.amarotest.activities.mainActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;


import com.test.amaro.amarotest.R;
import com.test.amaro.amarotest.data.model.CatalogueResponse;
import com.test.amaro.amarotest.data.model.ProductsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mMainActivityPresenter = new MainActivityPresenter(this);

    }

    private CatalogueAdapter.ListItemClickListener listItemClickListener = new CatalogueAdapter.ListItemClickListener() {
        @Override
        public void onListItemClick(ProductsItem product) {
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

    }

    @Override
    public void toggleTryAgain(int status) {

    }

    @Override
    public void showProducts(List<ProductsItem> productsItemList) {

        mCatalogueAdapter = new CatalogueAdapter(getBaseContext(), listItemClickListener);

        mLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCatalogueAdapter);
        mCatalogueAdapter.replaceData(productsItemList);
        mTvProductsCount.setText(String.format(getString(R.string.products_count), String.valueOf(mCatalogueAdapter.getItemCount())));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }
}
