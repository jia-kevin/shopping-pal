package me.tigerhe.shoppingpal.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.tigerhe.shoppingpal.R;
import me.tigerhe.shoppingpal.models.AmazonProduct;

/**
 * Created by kevin on 2017-06-01.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.listViewHolder> {
    private List<AmazonProduct> mProducts;
    private Context mContext;

    public ListAdapter(Context context, List<AmazonProduct> products) {
        mProducts = products;
        mContext = context;
    }

    public static class listViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public listViewHolder(View view) {
            super(view);
            mView = view;
        }
    }

    @Override
    public listViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_card, parent);
        listViewHolder viewHolder = new listViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(listViewHolder holder, int position) {
        TextView price = (TextView) holder.mView.findViewById(R.id.purchased_price);
        TextView name = (TextView) holder.mView.findViewById(R.id.purchased_name);
        TextView quantity = (TextView) holder.mView.findViewById(R.id.quantity_purchased);
        AmazonProduct product = mProducts.get(position);
        name.setText(product.getName());
        price.setText(String.valueOf(product.getAmount()*product.getPrice()));
        quantity.setText(String.valueOf(product.getAmount()));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}
