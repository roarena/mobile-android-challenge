package com.test.amaro.amarotest.activities.detailsActivity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.amaro.amarotest.R;
import com.test.amaro.amarotest.data.model.ProductsItem;
import com.test.amaro.amarotest.utils.C;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements DetailsActivityContract.View {
    private DetailsActivityContract.Presenter mDetailsActivityPresenter;
    private ProductsItem mProduct;
    private SizesAdapter mSizesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @BindView(R.id.iv_details_product_image)
    ImageView mIvProductImage;

    @BindView(R.id.tv_details_product_name)
    TextView mTvProductName;

    @BindView(R.id.tv_details_product_price)
    TextView mTvProductPrice;

    @BindView(R.id.tv_details_product_installments)
    TextView mTvProductInstallments;

    @BindView(R.id.tv_details_product_old_price)
    TextView mTvProductOldPrice;

    @BindView(R.id.cl_old_price)
    ConstraintLayout mClOldPriceLayout;

    @BindView(R.id.rv_details_size)
    RecyclerView rvSizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        mDetailsActivityPresenter = new DetailsActivityPresenter(getBaseContext(), this);

        if (getIntent().hasExtra(C.PRODUCT_BUNDLE)) {
            mProduct = Parcels.unwrap(getIntent().getParcelableExtra(C.PRODUCT_BUNDLE));
        }

        mSizesAdapter = new SizesAdapter(getBaseContext());
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvSizes.setLayoutManager(mLayoutManager);
        rvSizes.setAdapter(mSizesAdapter);

        mSizesAdapter.replaceData(mProduct.getSizes());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDetailsActivityPresenter.start();
    }

    @Override
    public void setPresenter(DetailsActivityContract.Presenter presenter) {
        if (presenter == null) {
            throw new NullPointerException("Presenter is null");
        }
        mDetailsActivityPresenter = presenter;
    }

    @Override
    public void setToolbarImage() {
        if (mProduct.getImage().isEmpty()) {
            Picasso.get().load(R.drawable.placeholder).into(mIvProductImage);
        } else {
            Picasso.get().load(mProduct.getImage()).placeholder(R.drawable.placeholder).into(mIvProductImage);
        }
    }

    @Override
    public void setProductsDetails() {
        mTvProductName.setText(mProduct.getName().isEmpty() ? getString(R.string.name_not_found) : mProduct.getName());
        mTvProductPrice.setText(mProduct.getActual_price().isEmpty() ? getString(R.string.price_not_found) : mProduct.getActual_price());
        mTvProductInstallments.setText(mProduct.getInstallments().isEmpty() ? getString(R.string.installments_not_found) : mProduct.getInstallments());

        if (mProduct.isOn_sale()) {
            mClOldPriceLayout.setVisibility(View.VISIBLE);
            mTvProductOldPrice.setText(mProduct.getRegular_price());
        } else {
            mClOldPriceLayout.setVisibility(View.GONE);
        }
    }
}
