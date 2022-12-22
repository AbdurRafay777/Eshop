package com.example.eshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshop.Activity.ProductDetailActivity;
import com.example.eshop.Product;
import com.example.eshop.R;
import com.example.eshop.databinding.ProductListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {

    Context context;
    ArrayList<Product> products;
    ArrayList<Product> filteredProducts;
    Filter filter;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
        this.filteredProducts = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);

        ProductViewHolder ph = new ProductViewHolder(v);
        return ph;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = filteredProducts.get(position);
        String price = Double.toString(product.getPrice());
        holder.bind.name.setText(product.getName());
        holder.bind.price.setText("Rs. " + price);
        Picasso.get().load(product.getImg()).into(holder.bind.img);

        holder.itemView.setOnClickListener((View v) -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("img", product.getImg());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredProducts.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new ProductsFilter();
        }
        return filter;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        ProductListItemBinding bind;
        public ProductViewHolder(@NonNull View v) {
            super(v);
            bind = ProductListItemBinding.bind(v);
        }
    }

    public class ProductsFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if(charSequence != null && charSequence.length() > 0){
                ArrayList<Product> filteredList = new ArrayList<>();
                for(int i=0; i < products.size(); i++){
                    if(products.get(i).getName().toLowerCase().contains(charSequence)){
                        filteredList.add(products.get(i));
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;

            }
            else{
                results.count = products.size();
                results.values = products;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredProducts = (ArrayList<Product>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
