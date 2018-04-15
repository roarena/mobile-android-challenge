package com.test.amaro.amarotest.activities.mainActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.amaro.amarotest.R;
import com.test.amaro.amarotest.data.model.ProductsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rodri on 14/04/2018.
 */

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.CatalogueAdapterViewHolder> {
    private Context mContext;
    private ListItemClickListener mOnClickListener;
    private List<ProductsItem> mDataSet;

    public class CatalogueAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_card_image)
        ImageView ivProductPicture;
        @BindView(R.id.tv_card_name)
        TextView tvProductName;
        @BindView(R.id.tv_card_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_card_installments)
        TextView tvProductInstallments;
        @BindView(R.id.tv_card_discount)
        TextView tvProductDiscount;

        private CatalogueAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickListener.onListItemClick(mDataSet.get(getAdapterPosition()), ivProductPicture);
                }
            });
        }
    }

    public CatalogueAdapter(Context context, ListItemClickListener clickListener) {
        mContext = context;
        mOnClickListener = clickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(ProductsItem product, View cardImage);
    }

    public void replaceData(List<ProductsItem> productsItems) {
        if (productsItems != null) {
            mDataSet = productsItems;
        }
        notifyDataSetChanged();
    }

    @Override
    public CatalogueAdapter.CatalogueAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_card, parent, false);

        return new CatalogueAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatalogueAdapterViewHolder holder, int position) {
        if (!mDataSet.get(position).getImage().isEmpty()) {
            Picasso.get().load(mDataSet.get(position).getImage()).placeholder(R.drawable.placeholder).into(holder.ivProductPicture);
        } else {
            Picasso.get().load(R.drawable.placeholder).into(holder.ivProductPicture);
        }

        if (mDataSet.get(position).isOn_sale() && !mDataSet.get(position).getDiscount_percentage().isEmpty()) {
            holder.tvProductDiscount.setVisibility(View.VISIBLE);
            holder.tvProductDiscount.setText(mContext.getString(R.string.discount_percentage, mDataSet.get(position).getDiscount_percentage()));
        } else {
            holder.tvProductDiscount.setVisibility(View.GONE);
        }

        holder.tvProductName.setText(mDataSet.get(position).getName().isEmpty() ? mContext.getString(R.string.name_not_found) : mDataSet.get(position).getName());
        holder.tvProductPrice.setText(mDataSet.get(position).getActual_price().isEmpty() ? mContext.getString(R.string.price_not_found) : mDataSet.get(position).getActual_price());
        holder.tvProductInstallments.setText(mDataSet.get(position).getInstallments().isEmpty() ? mContext.getString(R.string.installments_not_found) : mDataSet.get(position).getInstallments());
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null) return 0;
        return mDataSet.size();
    }
}
