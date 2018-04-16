package com.test.amaro.amarotest.activities.detailsActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.amaro.amarotest.R;
import com.test.amaro.amarotest.data.model.SizesItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rodri on 15/04/2018.
 */

public class SizesAdapter extends RecyclerView.Adapter<SizesAdapter.SizesAdapterViewHolder> {
    private Context mContext;
    private List<SizesItem> mDataSet;

    public class SizesAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_product_size)
        TextView tvProductSize;

        private SizesAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public SizesAdapter(Context context) {
        mContext = context;
    }

    public void replaceData(List<SizesItem> productSizes) {
        if (productSizes != null) {
            mDataSet = productSizes;
        }
        notifyDataSetChanged();
    }

    @Override
    public SizesAdapter.SizesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.size_item, parent, false);

        return new SizesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SizesAdapterViewHolder holder, int position) {
        holder.tvProductSize.setText(mDataSet.get(position).getSize().isEmpty() ?
                mContext.getString(R.string.size_not_found) : mDataSet.get(position).getSize());
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null) return 0;
        return mDataSet.size();
    }
}
