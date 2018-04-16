package com.test.amaro.amarotest.activities.mainActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.test.amaro.amarotest.R;
import com.test.amaro.amarotest.activities.detailsActivity.DetailsActivity;
import com.test.amaro.amarotest.data.model.ProductsItem;
import com.test.amaro.amarotest.data.repository.ProductsCache;
import com.test.amaro.amarotest.utils.C;

import org.parceler.Parcels;

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

    @BindView(R.id.filter_radio_group)
    RadioGroup mRgFilterGroup;

    @BindView(R.id.iv_main_filter)
    ImageView mIvFilter;

    @BindView(R.id.sw_main_sale)
    Switch mSwSale;

    @BindView(R.id.btn_main_clear_filters)
    Button mBtnClearFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mCatalogueAdapter = new CatalogueAdapter(getBaseContext(), listItemClickListener);
        mLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCatalogueAdapter);

        mMainActivityPresenter = new MainActivityPresenter(getBaseContext(), this);

        mBtnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivityPresenter.loadProducts();
            }
        });

        mIvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivityPresenter.onFilterClick(mRgFilterGroup.getVisibility());
            }
        });

        mSwSale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onSaleClick();
            }
        });

        mRgFilterGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                filterClick(i);
            }
        });

        mBtnClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFilterClicked();
            }
        });
    }

    private CatalogueAdapter.ListItemClickListener listItemClickListener = new CatalogueAdapter.ListItemClickListener() {
        @Override
        public void onListItemClick(ProductsItem product, View cardImage) {
            Log.d(C.LOG_TAG, product.getName());
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);

            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(MainActivity.this, cardImage, "productImage");

            intent.putExtra(C.PRODUCT_BUNDLE, Parcels.wrap(product));
            startActivity(intent, options.toBundle());
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // Prevent loading from internet multiple times.
        if (mCatalogueAdapter.getItemCount() < 1)
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
    public void showProducts(List<ProductsItem> productsItemList, int sortType) {
        mCatalogueAdapter.replaceData(ProductsCache.getInstance().retrieveList(sortType, productsItemList));
    }

    @Override
    public void setSubtitle(String subtitle) {
        mTvProductsCount.setText(subtitle);
    }

    @Override
    public void toggleFilterUi(int status) {
        mRgFilterGroup.setVisibility(status);
        mBtnClearFilter.setVisibility(status);
    }

    @Override
    public void onSaleClick() {
        if (mSwSale.isChecked()) {
            mCatalogueAdapter.replaceData(ProductsCache.getInstance().retrieveList(C.SORT_SALE, mCatalogueAdapter.getList()));
        } else {
            mCatalogueAdapter.replaceData(ProductsCache.getInstance().getmOriginalList());
        }
    }

    @Override
    public void filterClick(int filterClicked) {
        switch (filterClicked) {
            case R.id.rb_filter_higher:
                mCatalogueAdapter.replaceData(ProductsCache.getInstance().retrieveList(C.SORT_HIGHER_FIRST, mCatalogueAdapter.getList()));
                break;
            case R.id.rb_filter_lower:
                mCatalogueAdapter.replaceData(ProductsCache.getInstance().retrieveList(C.SORT_LOWER_FIRST, mCatalogueAdapter.getList()));
                break;
            default:
                mCatalogueAdapter.replaceData(ProductsCache.getInstance().getmOriginalList());
        }
    }

    @Override
    public void clearFilterClicked() {
        mCatalogueAdapter.replaceData(ProductsCache.getInstance().retrieveList(C.SORT_ORIGINAL, ProductsCache.getInstance().getmOriginalList()));
        if (mSwSale.isChecked())
            mSwSale.setChecked(false);
        mRgFilterGroup.clearCheck();
    }

    @Override
    public void enableUiFilters() {
        mSwSale.setEnabled(true);
        mBtnClearFilter.setEnabled(true);
        mRgFilterGroup.setEnabled(true);
    }
}
